package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOERingPipeline
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton

class FancyAuton : MOEAuton() {

    override val initialPose = Pose2d(-24.0, 60.0, 180.0)

    companion object {
        val INTAKE_LOCATION = Pose2d(-24.0, 36.0)
        val DEPOT_0_RING = Pose2d(12.0, 60.0)
        val DEPOT_1_RING = Pose2d(36.0, 36.0)
        val DEPOT_4_RING = Pose2d(12.0, 60.0)

    }

    override fun run() {
        val ringCount = (robot.opencv.pipeline as MOERingPipeline)

    }

}
