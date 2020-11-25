package org.firstinspires.ftc.teamcode.constants

import android.content.Context
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBot


// The 'i' prefix is used to avoid naming conflicts.
interface OpModeInterface {
    fun isActive(): Boolean
    val iIsStopRequested: Boolean
    fun iRequestOpModeStop()
}


object Ref {
    lateinit var moeOpMode: OpModeInterface
    lateinit var telemetry: Telemetry
    lateinit var hardwareMap: HardwareMap
    lateinit var appContext: Context
    lateinit var robot: MOEBot

    fun setRefs(opMode: OpModeInterface) {
        if (opMode !is OpMode) throw IllegalStateException("How is opmode not an OpMode")
        moeOpMode = opMode
        telemetry = opMode.telemetry
        hardwareMap = opMode.hardwareMap
        appContext = hardwareMap.appContext
    }

    fun setRobotRef(robot: MOEBot) {
        this.robot = robot
    }
}
