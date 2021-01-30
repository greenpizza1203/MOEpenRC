package org.firstinspires.ftc.teamcode.test

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.ParcelUuid
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEBotSubSystemConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.PPSystem
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import processing.ppsimulator.options
import java.lang.reflect.Constructor
import java.net.*
import java.util.*


@TeleOp
class PurePursuitTest : MOETeleOp() {
    val system = PPSystem(listOf(0.0, 0.0, 20.0, 20.0).zipWithNext { a, b -> PPPoint(a, b) }.toMutableList(), options)


    override fun mainLoop() {
        robot.runner.localizer.update()
        val pose = robot.runner.localizer.poseEstimate
        telemetry.addData("pose", pose)
//        system.getWheelVelocities()

    }

    override fun getRobotSubSystemConfig(): MOEBotSubSystemConfig {
        return MOEBotSubSystemConfig(useRR = true)
    }

}

