package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive

@Autonomous(group = "drive")
class PracticeAutonomous : LinearOpMode() {

    val timer = ElapsedTime()
    fun wait(waitTime: Double) {
        timer.reset()
        while (timer.time() < waitTime && opModeIsActive()) {

        }
    }

    fun shootRing() {
        trigger.setPosition(0.6)
        wait(0.35)
        trigger.setPosition(0.2)
    }

    lateinit var intakeMotor: DcMotor
    lateinit var outerShooterMotor: DcMotorEx
    lateinit var innerShooterMotor: DcMotorEx
    lateinit var trigger: Servo
    val Velocity = 2000

    override fun runOpMode() {
        intakeMotor = hardwareMap.dcMotor["INM13"]
        outerShooterMotor = hardwareMap.dcMotor["OFM10"] as DcMotorEx
        innerShooterMotor = hardwareMap.dcMotor["IFM11"] as DcMotorEx
        trigger = hardwareMap.servo["STS11"]
        var Config: Int = 1
//      Temporary Config for testing


        val drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(-60.0, 24.0, Math.toRadians(180.0))

        drive.poseEstimate = startPose

        val traj1: Trajectory = drive.trajectoryBuilder(startPose)
//                .splineTo(Vector2d(-24.0, 36.0), Math.toRadians(0.0))
                .lineTo(Vector2d(-24.0, 36.0))
                //intake stacked rings
                .build()

        val Configuration1: Trajectory = drive.trajectoryBuilder(traj1.end())
                .splineTo(Vector2d(12.0, 60.0), Math.toRadians(180.0))
                .splineTo(Vector2d(12.0, 72.0), Math.toRadians(180.0))
                .build()
        val Configuration2: Trajectory = drive.trajectoryBuilder(traj1.end())
                .splineTo(Vector2d(36.0, 36.0), Math.toRadians(180.0))
                .splineTo(Vector2d(12.0, 36.0), Math.toRadians(180.0))
                .build()
        val Configuration3: Trajectory = drive.trajectoryBuilder(traj1.end())
                .splineTo(Vector2d(60.0, 60.0), Math.toRadians(180.0))
                .splineTo(Vector2d(12.0, 60.0), Math.toRadians(180.0))
                .build()

        waitForStart()


//Start Turned
//        outerShooterMotor.velocity = Velocity.toDouble()
//        innerShooterMotor.velocity = Velocity.toDouble()
//
//        shootRing()
//
//        drive.turn(Math.toRadians(2.0))
//
//        shootRing()
//
//        drive.turn(Math.toRadians(2.0))
//        shootRing()

//        outerShooterMotor.velocity = 0.0
//        innerShooterMotor.velocity = 0.0

//        drive.turn(Math.toRadians(-5.0))

        drive.followTrajectory(traj1)
//
//        when {
//            Config == 1 -> drive.followTrajectory(Configuration1)
//            Config == 2 -> drive.followTrajectory(Configuration2)
//            Config == 3 -> drive.followTrajectory(Configuration3)
//
//        }
    }
}