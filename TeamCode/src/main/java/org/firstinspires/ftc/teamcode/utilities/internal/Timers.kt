package org.firstinspires.ftc.teamcode.utilities.internal

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.constants.OpModeInterface

private val timer by lazy { ElapsedTime() }

infix fun OpModeInterface.wait(milliseconds: Number) {
    timer.reset()
    val length = milliseconds.toLong()
    while (timer.milliseconds() < length && iOpModeIsActive()) {
//        telemetry.addData("please wait another${timer.seconds()} seconds")
//        telemetry.update()
    }
}

infix fun OpModeInterface.waitSeconds(seconds: Number) = this.wait(seconds.toLong() * 1000)


//infix fun OpModeInterface.waitA() {
//    val time = ElapsedTime()
//    val length = milliseconds.toLong()
//    while (! && iOpModeIsActive());
//}