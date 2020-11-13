package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid

import org.firstinspires.ftc.teamcode.constants.Ref.telemetry
import kotlin.math.abs

open class MOERawPid(options: MOEPidOptions) : MOEPidStructure<Double, Double> {

    //    var printWriter = PrintWriter(Environment.getExternalStorageDirectory().toString() + "/FirstLogs/Test.txt")
    var internalPid = MOEPid(options)

    override var input = { 0.0 }
    override var output: (Double) -> Unit = { }
    var threshold = 0.5
    var dActualTolerance = 0.05
    override var setpoint: () -> Double = { 0.0 }
    override var endCondition: (Double, Double, Double) -> Boolean = { actual, setpoint, dActual ->
        telemetry.addData("output", output)
        telemetry.addData("actual", actual)
        val error = internalPid.getError(setpoint, actual)
        telemetry.addData("error", error)
        telemetry.addData("dActual", dActual)
        telemetry.update()
        val b = abs(error) < threshold && dActual < dActualTolerance
        b
//        printWriter.println("$output\t$actual")

//        if (b) {
//            printWriter.close()
//            telemetry.addData("output", output).setRetained(true)
//            telemetry.addData("actual", actual).setRetained(true)
//            telemetry.addData("error", error).setRetained(true)
//            telemetry.addData("dActual", dActual).setRetained(true)

//            telemetry.addData("error", ).setRetained(true)
//            telemetry.update()
//            while (moeOpMode.iOpModeIsActive()) {
//                telemetry.addData("angle", robot.gyro.angle)
//                telemetry.update()
//                robot.chassis.stop()
//            }
//            moeOpMode.iRequestOpModeStop()
    }
//    b


    fun setOptions(options: MOEPidOptions) {
        internalPid.P = options.P
        internalPid.I = options.I
        internalPid.D = options.D
        internalPid.F = options.F

    }

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
