package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.firstinspires.ftc.teamcode.constants.Ref.robot

interface MOEPidStructure<I, O> {
//    var lastValue: T

    var input: () -> I
    var output: ((O) -> Unit)
    var setpoint: () -> I
    var endCondition: (I, I, I) -> Boolean
    fun run(sync: Boolean = true): Job {
        return GlobalScope.launch {
            reset()
            var iteration = 0
//            val startTime = System.currentTimeMillis()
            while (moeOpMode.iOpModeIsActive()) {
                val curInput = input()
                val curSetPoint = setpoint()
                val dActual = getAbsActualDiff()
                val neededOutput = getOutput(curInput, curSetPoint)
                iteration++
                if (endCondition(curInput, curSetPoint, dActual)) {
//                    Log.e("iteration", iteration.toString())

//                    Log.e("time", (System.currentTimeMillis() - startTime).toString())
                    break
                }
                if (moeOpMode.iIsStopRequested) break
                output(neededOutput)
            }
            onFinish()
        }.also {
            if (sync) {
                runBlocking { it.join() }
            }
        }
    }

    fun getAbsActualDiff(): I
    fun reset()
    fun onFinish() {
        robot.chassis.stop()
    }

    //    fun getOutput(input: I): O
    fun getOutput(input: I, setpoint: I): O

}
