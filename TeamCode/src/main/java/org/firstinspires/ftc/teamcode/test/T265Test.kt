package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOESlam
import org.firstinspires.ftc.teamcode.teleop.UltimateGoalTeleOp


//val slamra by lazy {  }

@TeleOp
class T265Test : UltimateGoalTeleOp() {
    lateinit var slam: MOESlam
    override fun initOpMode() {
        super.initOpMode()
        slam = MOESlam()
    }

    override fun mainLoop() {
        super.mainLoop()
        telemetry.addData("Pose", slam.pose())
    }
}