package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
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
        trigger.setPosition(0.7)
        wait(0.8)
        trigger.setPosition(0.2)
    }

    lateinit var intakeMotor: DcMotor
    lateinit var outerShooterMotor: DcMotorEx
    lateinit var innerShooterMotor: DcMotorEx
    lateinit var trigger: Servo
    lateinit var grabber: Servo
    lateinit var arm: DcMotor
    val Velocity = 1750

    override fun runOpMode() {
        intakeMotor = hardwareMap.dcMotor["INM13"]
        outerShooterMotor = hardwareMap.dcMotor["OFM10"] as DcMotorEx
        innerShooterMotor = hardwareMap.dcMotor["IFM11"] as DcMotorEx
        trigger = hardwareMap.servo["RTS25"]
        grabber = hardwareMap.servo["GWS21"]
        arm = hardwareMap.get(DcMotorEx::class.java,"WAM12")
        arm.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        var Config: Int = 2
//      Temporary Config for testing


        val drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(-60.0, 24.0, Math.toRadians(180.0))

        drive.poseEstimate = startPose

        val traj1: Trajectory = drive.trajectoryBuilder(startPose)
//               .splineTo(Vector2d(-24.0, 36.0), Math.toRadians(180.0))
                .lineToLinearHeading(Pose2d(-24.0, 36.0,Math.toRadians(0.0)))
//               .lineTo(Vector2d(-24.0, 36.0))
                //intake stacked rings
                .build()

        val depotLocation = when(Config){
            1-> Vector2d(12.0,60.0)
            2-> Vector2d(36.0,36.0)
            else -> Vector2d(60.0,60.0)
        }

        val depotConfig = drive.trajectoryBuilder(traj1.end())
                .splineTo(depotLocation, Math.toRadians(0.0))
                .build()

        // val backwardsDistance = (Config - 1)*24.0
        val backwardsDistance = when(Config){
                1-> 0.0
                2-> 24.0
                else-> 48.0
        }

        val backward = drive.trajectoryBuilder(depotConfig.end())
                .back(backwardsDistance)
                .build()

        val tempConfig: Trajectory = drive.trajectoryBuilder(startPose)
                .lineTo(Vector2d(-12.0,16.0))
                .build()
        val tempConfig2: Trajectory = drive.trajectoryBuilder(tempConfig.end())
                .lineTo(Vector2d(0.0,36.0))
                .build()
        val tempConfig3: Trajectory = drive.trajectoryBuilder(tempConfig2.end())
                .back(16.0)
                .build()

        waitForStart()
        grabber.position = 0.05
//        outerShooterMotor.velocity = Velocity.toDouble()
//        innerShooterMotor.velocity = Velocity.toDouble()

//        drive.turn(Math.toRadians(-2.045))
//        wait(0.1)
//        shootRing()
//
//        drive.turn(Math.toRadians(-3.695))
//        wait(0.1)
//        shootRing()
//
//        drive.turn(Math.toRadians(-3.695))
//        wait(0.1)
//        shootRing()
//
//        outerShooterMotor.velocity = 0.0
//        innerShooterMotor.velocity = 0.0
//
//        drive.turn(Math.toRadians(5.2))

//        drive.followTrajectory(traj1)
//        drive.followTrajectory(depotConfig)
//        drive.followTrajectory(backward)

        drive.followTrajectory(tempConfig)
        drive.followTrajectory(tempConfig2)

        telemetry.addData("inVelocity", innerShooterMotor.velocity)
        telemetry.addData("outVelocity", outerShooterMotor.velocity)

        outerShooterMotor.velocity = Velocity.toDouble()
        innerShooterMotor.velocity = Velocity.toDouble()
        wait(1.5)
        shootRing()
        wait(1.5)
        shootRing()
        wait(1.5)
        shootRing()


        outerShooterMotor.velocity = 0.0
        innerShooterMotor.velocity = 0.0

        drive.followTrajectory(tempConfig3)
        arm.power = 0.4
        wait(0.6)
        arm.power = 0.0
        grabber.position = 0.25
        }
    }
