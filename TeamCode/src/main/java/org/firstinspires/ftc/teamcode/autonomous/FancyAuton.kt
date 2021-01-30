package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEBotSubSystemConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians


@Autonomous
class FancyAuton : MOEAuton() {

    override val initialPose = Pose2d(-24.0, 60.0, 180.0)

    companion object {
        val INTAKE_LOCATION = Pose2d(-24.0, 36.0)
        val DEPOT_0_RING = Pose2d(12.0, 60.0)
        val DEPOT_1_RING = Pose2d(36.0, 36.0)
        val DEPOT_4_RING = Pose2d(12.0, 60.0)

    }

    //    private val ringPipe = MOERingPipeline(x = 34, y = 210, width = 90, height = 61)
//    override val openCVConfig = MOEPenCVConfig(ringPipe, camera = "RingCam")
    var dashboard = FtcDashboard.getInstance()


    override fun run() {

//        telemetry.addData("ring count", ringPipe)
//        telemetry.update()
        val localizer = robot.runner.localizer
        localizer.poseEstimate = Pose2d(0.0, 0.0, 180.0.toRadians())
        localizer.update()

        val moves = robot.runner.trajectory {
            splineTo(Vector2d(40.0, 40.0), 0.0)
            splineTo(Vector2d(80.0, 80.0), 0.0)
        }
//        TrajectoryFollower()
        waitForStart()
//        follower.followTrajectory(trajectory!!)

//        robot.runner.setDrivePower(Pose2d(1.0, 0.0, 0.0))
        val follower = robot.runner.follower
        follower.followTrajectory(moves)
        while (opModeIsActive()) {
            localizer.update()
            val signal = follower.update(localizer.poseEstimate)
            robot.runner.setDriveSignal(signal)
            telemetry.addData("signal", signal)
            telemetry.addData("current", localizer.poseEstimate)
            telemetry.addData("target", moves[follower.elapsedTime()])
            telemetry.addData("follower", follower.isFollowing())
            telemetry.update()
        }

//        robot.
//        ringPipe.requestFrame()
    }

    override fun getRobotSubSystemConfig() = MOEBotSubSystemConfig(useRR = true)

}

