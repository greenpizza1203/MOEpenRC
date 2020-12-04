package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

import com.qualcomm.robotcore.util.ElapsedTime

@Autonomous(group = "drive")
class PracticeAutonomous : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val Velocity = 2000
        val timer = ElapsedTime()
        var Config: Int
        Config = 1
//      Temporary Config for testing


        val drive =SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(48.0, 12.0, Math.toRadians(0.0))

        drive.poseEstimate = startPose

        val traj1: Trajectory = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(36.0, 36.0), Math.toRadians(0.0))
                //intake stacked rings
                .build()

        val Configuration1: Trajectory = drive.trajectoryBuilder(traj1.end())
                .splineTo(Vector2d(12.0, 84.0), Math.toRadians(0.0))
                .splineTo(Vector2d(12.0, 72.0), Math.toRadians(0.0))
                .build()
        val Configuration2: Trajectory = drive.trajectoryBuilder(traj1.end())
                .splineTo(Vector2d(36.0, 108.0), Math.toRadians(0.0))
                .splineTo(Vector2d(12.0, 72.0), Math.toRadians(0.0))
                .build()
        val Configuration3: Trajectory = drive.trajectoryBuilder(traj1.end())
                .splineTo(Vector2d(12.0, 132.0), Math.toRadians(0.0))
                .splineTo(Vector2d(12.0, 72.0), Math.toRadians(0.0))
                .build()

        waitForStart()

        if (isStopRequested()){
            return
        }

        drive.turn(Math.toRadians(1.0))
        outerShooterMotor.velocity = Velocity
        innerShooterMotor.velocity = Velocity

        trigger.setPosition(0.6)
        wait(0.35)
        trigger.setPosition(0.2)

        drive.turn(Math.toRadians(2.0))
        timer.reset()

        trigger.setPosition(0.6)
        wait(0.35)
        trigger.setPosition(0.2)

        drive.turn(Math.toRadians(2.0))
        timer.reset()

        trigger.setPosition(0.6)
        wait(0.35)
        trigger.setPosition(0.2)

        outerShooterMotor.velocity = 0
        innerShooterMotor.velocity = 0

        drive.turn(Math.toRadians(-5.0))

        drive.followTrajectory(traj1)

        when {
            Config == 1 -> drive.followTrajectory(Configuration1)
            Config == 2 -> drive.followTrajectory(Configuration2)
            Config == 3 -> drive.followTrajectory(Configuration3)

        }
    }
    fun shootRing(){
            trigger.setPosition(0.6)
        wait(0.35)
        trigger.setPosition(0.2)
        }

        fun wait(waitTime:Double){
            timer.reset()
            while(timer.time() < waitTime&& opModeIsActive) {

            }
        }
}