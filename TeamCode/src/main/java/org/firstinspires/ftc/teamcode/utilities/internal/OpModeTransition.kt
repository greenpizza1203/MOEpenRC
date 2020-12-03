package org.firstinspires.ftc.teamcode.utilities.internal

import android.util.Log
import com.qualcomm.ftccommon.CommandList
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.robocol.Command
import kotlin.reflect.KClass

fun initOtherOpMode(opMode: KClass<TeleOp>) {

//    val name = getOpModeName(opMode.java) ?: return
    Log.e("handlingCommand", opMode.toString())
//    moeOpMode.iRequestOpModeStop()

//    activityRef?.eventLoop?.opModeManager?.apply {
//        thread {
//            stopActiveOpMode()
//            initActiveOpMode(opMode)
//        }

}

fun main() {
    val name = getOpModeName(TeleOp::class.java) ?: return
    println(name)
    val command = getInitCommand(name)
    println(command)
}


fun getOpModeName(clazz: Class<*>): String? {
    var name = when {
        clazz.isAnnotationPresent(TeleOp::class.java) -> clazz.getAnnotation(TeleOp::class.java)?.name
        clazz.isAnnotationPresent(Autonomous::class.java) -> clazz.getAnnotation(Autonomous::class.java)?.name
        else -> clazz.simpleName
    }
    if (name.isNullOrEmpty()) name = clazz.simpleName
    return name
}


fun getInitCommand(annotation: String): Command {
    return Command(CommandList.CMD_INIT_OP_MODE, annotation)
}
