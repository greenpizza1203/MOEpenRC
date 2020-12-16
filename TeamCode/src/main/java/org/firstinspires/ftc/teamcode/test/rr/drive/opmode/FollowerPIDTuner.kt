package org.firstinspires.ftc.teamcode.test.rr.drive.opmode

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toDegrees

/*
* Op mode for tuning follower PID coefficients (located in the drive base classes). The robot
* drives in a DISTANCE-by-DISTANCE square indefinitely.
*/
@Config
@Autonomous(group = "drive")
class FollowerPIDTuner : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        var startPose = Pose2d(-DISTANCE / 2, -DISTANCE / 2, 0.0)
        drive.poseEstimate = startPose
        waitForStart()
        repeat(4) {
            val traj = drive.trajectoryBuilder(startPose)
                    .forward(DISTANCE)
                    .build()
            drive.followTrajectory(traj)
            drive.turn(Math.toRadians(90.0))
            startPose = traj.end().plus(Pose2d(0.0, 0.0, Math.toRadians(90.0)))
        }
        while (opModeIsActive()) {
            val poseEstimate = drive.poseEstimate
            telemetry.addData("poseX", poseEstimate.x)
            telemetry.addData("poseY", poseEstimate.y)
            telemetry.addData("poseHeading", poseEstimate.heading.toDegrees())
            telemetry.update()
            drive.localizer.update()
        }
    }

    companion object {
        var DISTANCE = 48.0 // in
    }
}