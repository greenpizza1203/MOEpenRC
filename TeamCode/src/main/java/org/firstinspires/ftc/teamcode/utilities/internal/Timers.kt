package org.firstinspires.ftc.teamcode.utilities.internal

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.constants.OpModeInterface

val timer by lazy { ElapsedTime() }

inline fun OpModeInterface.wait(milliseconds: Number, func: () -> Unit = { Thread.yield() }) {
    timer.reset()
    val length = milliseconds.toLong()
    while (timer.milliseconds() < length && isActive()) func()
}

infix fun OpModeInterface.waitSeconds(seconds: Number) = this.wait(seconds.toLong() * 1000)


//infix fun OpModeInterface.waitA() {
//    val time = ElapsedTime()
//    val length = milliseconds.toLong()
//    while (! && iOpModeIsActive());
//}