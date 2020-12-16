package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive

@Autonomous(group = "drive")
class ParkAuton : LinearOpMode() {


    override fun runOpMode() {

        val drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(48.0, 9.0, Math.toRadians(0.0))
        drive.poseEstimate = startPose
        val traj1: Trajectory = drive.trajectoryBuilder(startPose)
                .back(75.0)
                .build()
        drive.followTrajectory(traj1)
//        while(opModeIsActive()){
//
//        }
    }
}