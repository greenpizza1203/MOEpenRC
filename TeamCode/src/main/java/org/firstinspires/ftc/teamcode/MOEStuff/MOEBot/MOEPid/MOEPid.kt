package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid

/**
 * Small, easy to use PID implementation with advanced controller capability.<br></br>
 * Minimal usage:<br></br>
 * MiniPID pid = new MiniPID(p,i,d); <br></br>
 * ...looping code...{ <br></br>
 * output= pid.getOutput(sensorvalue,target); <br></br>
 * }
 *
 * @see http://brettbeauregard.com/blog/2011/04/improving-the-beginners-pid-direction/improving-the-beginners-pid-introduction
 */
data class MOEPidOptions(val P: Double = 0.0,
                         val I: Double = 0.0,
                         val D: Double = 0.0,
                         val F: Double = 0.0)

open class MOEPid(var P: Double = 0.0,
                  I: Double = 0.0,
                  var D: Double = 0.0,
                  var F: Double = 0.0) {
    constructor(options: MOEPidOptions) : this(options.P, options.I, options.D, options.F)

    //**********************************
    // Class private variables
    //**********************************

    private var lastTime: Long = 0
    var I = I
        set(value) {
            if (field != 0.0) {
                errorSum = errorSum * field / value
            }
            if (maxIOutput != 0.0) {
                maxError = maxIOutput / value
            }
            field = value
        }


    private var maxIOutput = 0.0
    private var maxError = 0.0
    private var errorSum = 0.0

    private var maxOutput = 0.0
    private var minOutput = 0.0

    var setpoint = 0.0

    var lastActual = 0.0

    private var firstRun = true
    private var reversed = false

    private var outputRampRate = 0.0
    private var lastOutput = 0.0

    private var outputFilter = 0.0

    private var setpointRange = 0.0

    /**
     * Configure the PID object.
     * See setP, setI, setD methods for more detailed parameters.
     * @param p Proportional gain. Large if large difference between setpoint and target.
     * @param i Integral gain.  Becomes large if setpoint cannot reach target quickly.
     * @param d Derivative gain. Responds quickly to large changes in error. Small values prevents P and I terms from causing overshoot.
     */
    fun setPID(p: Double, i: Double, d: Double) {
        P = p
        I = i
        D = d
    }

    /**
     * Configure the PID object.
     * See setP, setI, setD, setF methods for more detailed parameters.
     * @param p Proportional gain. Large if large difference between setpoint and target.
     * @param i Integral gain.  Becomes large if setpoint cannot reach target quickly.
     * @param d Derivative gain. Responds quickly to large changes in error. Small values prevents P and I terms from causing overshoot.
     * @param f Feed-forward gain. Open loop "best guess" for the output should be. Only useful if setpoint represents a rate.
     */
    fun setPID(p: Double, i: Double, d: Double, f: Double) {
        P = p
        I = i
        D = d
        F = f
    }

    /**
     * Set the maximum output value contributed by the I component of the system
     * This can be used to prevent large windup issues and make tuning simpler
     * @param maximum Units are the same as the expected output value
     */
    fun setMaxIOutput(maximum: Double) {
        // Internally maxError and Izone are similar, but scaled for different purposes.
        // The maxError is generated for simplifying math, since calculations against
        // the max error are far more common than changing the I term or Izone.
        maxIOutput = maximum
        if (I != 0.0) {
            maxError = maxIOutput / I
        }
    }

    /**
     * Specify a maximum output range. <br></br>
     * When one input is specified, output range is configured to
     * **[-output, output]**
     * @param output
     */
    fun setOutputLimits(output: Double) = setOutputLimits(-output, output)

    /**
     * Specify a  maximum output.
     * When two inputs specified, output range is configured to
     * **[minimum, maximum]**
     * @param minimum possible output value
     * @param maximum possible output value
     */
    fun setOutputLimits(minimum: Double, maximum: Double) {
        if (maximum < minimum) return
        maxOutput = maximum
        minOutput = minimum

        // Ensure the bounds of the I term are within the bounds of the allowable output swing
        if (maxIOutput == 0.0 || maxIOutput > maximum - minimum) {
            setMaxIOutput(maximum - minimum)
        }
    }

    /**
     * Set the operating direction of the PID controller
     * @param reversed Set true to reverse PID output
     */
    fun setDirection(reversed: Boolean) {
        this.reversed = reversed
    }

    //**********************************
    // Primary operating functions
    //**********************************


    /**
     * Calculate the output value for the current PID cycle.<br></br>
     * In one parameter mode, the last configured setpoint will be used.<br></br>
     * @see MiniPID.setSetpoint
     * @param actual The monitored value, typically as a sensor input.
     * @param setpoint The target value for the system
     * @return calculated output value for driving the system
     */
    fun getOutput(actual: Double, newSetPoint: Double = setpoint): Double {
        var tempSetPoint = newSetPoint

        this.setpoint = tempSetPoint

        // Ramp the setpoint used for calculations if user has opted to do so
        if (setpointRange != 0.0) {
            tempSetPoint = tempSetPoint.coerceIn(actual - setpointRange..actual + setpointRange)
        }

        // Do the simple parts of the calculations
        val error = getError(tempSetPoint, actual)

        // Calculate F output. Notice, this depends only on the setpoint, and not the error.
        val FOutput = F * tempSetPoint

        // Calculate P term
        var Poutput = P * error

        // If this is our first time running this, we don't actually _have_ a previous input or output.
        // For sensor, sanely assume it was exactly where it is now.
        // For last output, we can assume it's the current time-independent outputs.
        if (firstRun) {
            lastActual = actual
            lastOutput = Poutput + FOutput
            firstRun = false
        }

        // Calculate D Term
        // Note, this is negative. This actually "slows" the system if it's doing
        // the correct thing, and small values helps prevent output spikes and overshoot
        val Doutput: Double = -D * (actual - lastActual)
        lastActual = actual

        // The Iterm is more complex. There's several things to factor in to make it easier to deal with.
        // 1. maxIoutput restricts the amount of output contributed by the Iterm.
        // 2. prevent windup by not increasing errorSum if we're already running against our max Ioutput
        // 3. prevent windup by not increasing errorSum if output is output=maxOutput
        var Ioutput = I * errorSum
        if (maxIOutput != 0.0) {
            Ioutput = Ioutput.coerceIn(-maxIOutput..maxIOutput)
        }

        // And, finally, we can just add the terms up
        var output = FOutput + Poutput + Ioutput + Doutput

        // Figure out what we're doing with the error.
        if (minOutput != maxOutput && !(output in minOutput..maxOutput)) {
            errorSum = error
            // reset the error sum to a sane level
            // Setting to current error ensures a smooth transition when the P term
            // decreases enough for the I term to start acting upon the controller
            // From that point the I term will build up as would be expected
        } else if (outputRampRate != 0.0 && !(output in lastOutput - outputRampRate..lastOutput + outputRampRate)) {
            errorSum = error
        } else if (maxIOutput != 0.0) {
            errorSum = (errorSum + error).coerceIn(-maxError..maxError)
            // In addition to output limiting directly, we also want to prevent I term
            // buildup, so restrict the error directly
        } else {
            errorSum += error
        }

        // Restrict output to our specified output and ramp limits
        if (outputRampRate != 0.0) {
            output = output.coerceIn(lastOutput - outputRampRate..lastOutput + outputRampRate)
        }
        if (minOutput != maxOutput) {
            output = output.coerceIn(minOutput..maxOutput)
        }
        if (outputFilter != 0.0) {
            output = lastOutput * outputFilter + output * (1 - outputFilter)
        }

        // Get a test printline with lots of details about the internal
        // calculations. This can be useful for debugging.
        //         System.out.printf("Final output %5.2f [ %5.2f, %5.2f , %5.2f  ], eSum %.2f\n",output,Poutput, Ioutput, Doutput,errorSum );
        //         System.out.printf("%5.2f\t%5.2f\t%5.2f\t%5.2f\n",output,Poutput, Ioutput, Doutput );

        lastOutput = output
        return output
    }

    var getError = { setpoint: Double, actual: Double -> setpoint - actual }
//    open fun getError(setPoint: Double, actual: Double) = setPoint - actual

    /**
     * Resets the controller. This erases the I term buildup, and removes
     * D gain on the next loop.<br></br>
     * This should be used any time the PID is disabled or inactive for extended
     * duration, and the controlled portion of the system may have changed due to
     * external forces.
     */

    fun reset() {
        firstRun = true
        errorSum = 0.0
    }

    /**
     * Set the maximum rate the output can increase per cycle.<br></br>
     * This can prevent sharp jumps in output when changing setpoints or
     * enabling a PID system, which might cause stress on physical or electrical
     * systems.  <br></br>
     * Can be very useful for fast-reacting control loops, such as ones
     * with large P or D values and feed-forward systems.
     *
     * @param rate, with units being the same as the output
     */
    fun setOutputRampRate(rate: Double) {
        outputRampRate = rate
    }

    /**
     * Set a limit on how far the setpoint can be from the current position
     * <br></br>Can simplify tuning by helping tuning over a small range applies to a much larger range.
     * <br></br>This limits the reactivity of P term, and restricts impact of large D term
     * during large setpoint adjustments. Increases lag and I term if range is too small.
     * @param range, with units being the same as the expected sensor range.
     */
    fun setSetpointRange(range: Double) {
        setpointRange = range
    }

    /**
     * Set a filter on the output to reduce sharp oscillations. <br></br>
     * 0.1 is likely a sane starting value. Larger values use historical data
     * more heavily, with low values weigh newer data. 0 will disable, filtering, and use
     * only the most recent value. <br></br>
     * Increasing the filter strength will P and D oscillations, but force larger I
     * values and increase I term overshoot.<br></br>
     * Uses an exponential wieghted rolling sum filter, according to a simple <br></br>
     * <pre>output*(1-strength)*sum(0..n){output*strength^n}</pre> algorithm.
     * @param output valid between [0..1), meaning [current output only.. historical output only)
     */
    fun setOutputFilter(strength: Double) {
        if (strength in 0.0..1.0) {
            outputFilter = strength
        }
    }

    override fun toString(): String {
        return "P=$P,I=$I,D=$D"
    }

}
