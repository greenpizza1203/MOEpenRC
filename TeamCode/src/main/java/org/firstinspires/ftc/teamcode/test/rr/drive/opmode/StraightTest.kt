package org.firstinspires.ftc.teamcode.test.rr.drive.opmode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.test.rr.drive.DriveConstants
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive

/*
 * This is a simple routine to test translational drive capabilities.
 */
@Config
@Autonomous(group = "drive")
class StraightTest : LinearOpMode() {
    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        val trajectory = drive.trajectoryBuilder(Pose2d(0.0, 0.0))
                .forward(DISTANCE)
//                .lineToLinearHeading(Pose2d(0.0, 0.0, PI))

//                .lineTo(Vector2d(0.0, 100.0)) //                .wait(1000)
                .build()
        val dash = FtcDashboard.getInstance().telemetry
        dash.addData("strafe", DISTANCE)
        dash.addData("kv", DriveConstants.kV)
        dash.addData("kA", DriveConstants.kA)
        dash.addData("kStatic", DriveConstants.kStatic)
        dash.update()
        waitForStart()
        if (isStopRequested) return
        drive.followTrajectoryAsync(trajectory)
        while (opModeIsActive()) {
            drive.update()
//            telemetry.addData("pose", (drive.localizer as LeftWheelOdo).getRawPositions())
            telemetry.addData("pose", drive.localizer.poseEstimate)
            telemetry.update()
        }
    }

    companion object {
        var DISTANCE = 35 * 12.0 // in
    }
}