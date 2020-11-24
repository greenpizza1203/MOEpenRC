package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid

import org.firstinspires.ftc.teamcode.constants.Ref.telemetry
import kotlin.math.abs

open class MOERawPid(options: MOEPidOptions) : MOEPidStructure<Double, Double> {

    var internalPid = MOEPid(options)

    override var input = { 0.0 }
    override var output: (Double) -> Unit = { }
    var threshold = 0.5
    var dActualTolerance = 0.05
    override var setpoint: () -> Double = { 0.0 }
    override var endCondition: (Double, Double, Double) -> Boolean = { actual, setpoint, dActual ->
//        telemetry.addData("output", output)
//        telemetry.addData("actual", actual)
        val error = internalPid.getError(setpoint, actual)
//        telemetry.addData("error", error)
//        telemetry.addData("dActual", dActual)
//        telemetry.update()
        abs(error) < threshold && dActual < dActualTolerance
    }
//    b


    fun setOptions(options: MOEPidOptions) {
        internalPid.P = options.P
        internalPid.I = options.I
        internalPid.D = options.D
        internalPid.F = options.F

    }

    fun getOutput() = getOutput(input(), setpoint())

    fun getOutput(input: Double) = getOutput(input, setpoint())
    override fun getOutput(input: Double, setpoint: Double): Double = internalPid.getOutput(input, setpoint)

    override fun reset() = internalPid.reset()
    fun setOutputLimits(limit: Double) = internalPid.setOutputLimits(limit)
    override fun getAbsActualDiff(): Double {
        return abs(internalPid.lastActual - input())
    }

    constructor(P: Double = 0.0,
                I: Double = 0.0,
                D: Double = 0.0,
                F: Double = 0.0) : this(MOEPidOptions(P, I, D, F))
}
