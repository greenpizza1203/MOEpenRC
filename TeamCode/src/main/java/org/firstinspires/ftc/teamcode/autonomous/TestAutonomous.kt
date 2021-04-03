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
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.centerX
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.MOEHighGoalPipeline
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.Target
import org.firstinspires.ftc.teamcode.autonomous.vision.BasicHighGoalPipeline
import org.firstinspires.ftc.teamcode.autonomous.vision.BasicRingPipeline
import org.firstinspires.ftc.teamcode.autonomous.vision.MOEPipelineAssist
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive
import java.util.*

@Autonomous(group = "drive")
class TestAutonomous : LinearOpMode() {

    val timer = ElapsedTime()
    fun wait(waitTime: Double) {
        timer.reset()
        while (timer.time() < waitTime && opModeIsActive()) {

        }
    }

    fun shootRing() {
        trigger.setPosition(0.85)
        wait(0.2)
        trigger.setPosition(0.2)
    }

    fun release() {
//        if(arm.currentPosition > -550){arm.velocity = 0.5}
//        wait(0.2)
//        grabber.position = 0.25
        ////////
        arm.power = 0.8//start putting out arm
        wait(0.25)
        arm.power = 0.0//stop arm
        wait(0.5)
        wait(0.5)
        wait(0.5)
        grabber.position = 0.25//drop the wobbly boi

        wait(0.5)//let things settle

        arm.power = 0.25//arm down more 2 grab next boi
        wait(0.1)
        arm.power = 0.0//stop arm
    }

    fun grab() {
//        if(arm.currentPosition > -700){arm.velocity = 0.5}
//        wait(0.2)
//        grabber.position = 0.05
//        if(arm.currentPosition < -565){arm.velocity = -0.5}
        //////
        grabber.position = 0.05
        wait(0.3)
        arm.power = -0.4
        wait(0.2)
        arm.power = 0.0
        wait(1.0)
//        arm.power = -0.4
//        wait(0.7)
//        arm.power = 0.0
    }


    lateinit var intakeMotor: DcMotor
    lateinit var outerShooterMotor: DcMotorEx
    lateinit var innerShooterMotor: DcMotorEx
    lateinit var trigger: Servo
    lateinit var grabber: Servo
    lateinit var arm: DcMotorEx
    val Velocity = 2100
    lateinit var opencvAssist: MOEPipelineAssist

    override fun runOpMode() {
        intakeMotor = hardwareMap.dcMotor["INM13"]
        outerShooterMotor = hardwareMap.dcMotor["OFM10"] as DcMotorEx
        innerShooterMotor = hardwareMap.dcMotor["IFM11"] as DcMotorEx
        trigger = hardwareMap.servo["RTS25"]
        grabber = hardwareMap.servo["WAS21"]
        arm = hardwareMap.get(DcMotorEx::class.java, "WAM12")
        arm.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

        val pipeline = BasicRingPipeline(x = 128, y = 202, width = 95, height = 61)
        opencvAssist = MOEPipelineAssist(hardwareMap, pipeline)

        val drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(-60.0, 24.0, Math.toRadians(180.0))

        drive.poseEstimate = startPose

        val tempConfig: Trajectory = drive.trajectoryBuilder(startPose)
                .lineTo(Vector2d(-12.0, 16.0))
                .build()
        val tempConfig2: Trajectory = drive.trajectoryBuilder(tempConfig.end())
                .lineTo(Vector2d(0.0, 36.0))
                .build()
        val tempConfig3: Trajectory = drive.trajectoryBuilder(tempConfig2.end())
                .back(16.0)
                .build()

        val Config1Part1: Trajectory = drive.trajectoryBuilder(startPose, true)
                .splineTo(Vector2d(-24.0, 36.0), Math.toRadians(0.0))
                .splineTo(Vector2d(0.0, 36.0), Math.toRadians(0.0))
                .build()

        val Config1Part2: Trajectory = drive.trajectoryBuilder(Config1Part1.end(), true)
                .splineTo(Vector2d(12.0, 40.0), Math.toRadians(82.0))
                .build()

        val Config1Part3: Trajectory = drive.trajectoryBuilder(Config1Part2.end(), true)
                .splineTo(Vector2d(-31.0, 48.0), Math.toRadians(208.0))
                .build()

        val Config1Part4: Trajectory = drive.trajectoryBuilder(Config1Part3.end().plus( Pose2d(0.0, 0.0, Math.toRadians(-35.0))), true)
                .forward(44.0)
                .build()


        val Config2Part1: Trajectory = drive.trajectoryBuilder(startPose, true)
                .splineTo(Vector2d(-12.0, 16.0), Math.toRadians(0.0))
                .splineTo(Vector2d(0.0, 36.0), Math.toRadians(0.0))
                .build()

        val Config2Part2: Trajectory = drive.trajectoryBuilder(Config2Part1.end(), true)
                .splineTo(Vector2d(20.0, 36.0), Math.toRadians(0.0))
                .build()

        val Config2Part3: Trajectory = drive.trajectoryBuilder(Config2Part2.end(), true)
                .splineTo(Vector2d(-12.0, 52.0), Math.toRadians(180.0))
                .splineTo(Vector2d(-35.0, 48.0), Math.toRadians(200.0))
                .build()

        val Config2Part4: Trajectory = drive.trajectoryBuilder(Config2Part3.end().plus(Pose2d(0.0,0.0,Math.toRadians(-20.0))), true)
                .forward(50.0)
                .build()


        val Config3Part1: Trajectory = drive.trajectoryBuilder(startPose, true)
                .splineTo(Vector2d(-12.0, 16.0), Math.toRadians(0.0))
                .splineTo(Vector2d(0.0, 36.0), Math.toRadians(0.0))
                .build()

        val Config3Part2: Trajectory = drive.trajectoryBuilder(Config3Part1.end(), true)
                .splineTo(Vector2d(42.0, 54.0), Math.toRadians(0.0))
                .build()

        val Config3Part3: Trajectory = drive.trajectoryBuilder(Config3Part2.end(), true)
                .splineTo(Vector2d(-36.0, 48.0), Math.toRadians(185.0))
                .build()

        val Config3Part3a: Trajectory = drive.trajectoryBuilder(Config3Part2.end(), true)
                .splineToSplineHeading(Pose2d(-32.0, 48.0, Math.toRadians(0.0)), Math.toRadians(185.0))
                .build()

        val Config3Part4: Trajectory = drive.trajectoryBuilder(Config3Part3.end(), true)
                .splineTo(Vector2d(42.0, 60.0), Math.toRadians(0.0))
                .build()

        val Config3Part4a: Trajectory = drive.trajectoryBuilder(Config3Part3.end(), true)
                .splineToSplineHeading(Pose2d(39.0, 54.0, Math.toRadians(0.0)), Math.toRadians(0.0))
                .build()

        val Config3Part5: Trajectory = drive.trajectoryBuilder(Config3Part4.end(),true)
                .forward(36.0)
                .build()

        grabber.position = 0.02
        telemetry.addLine("Ready!")
        telemetry.update()
        waitForStart()

        var Config = pipeline.count
        telemetry.addData("RingCount", Config)
        telemetry.update()

        if(Config == -3){

        }
        if (Config == -1) {
            telemetry.addData("arm", arm.getCurrentPosition())
            telemetry.update()
            wait(5.0)
            telemetry.addLine("release")
            telemetry.update()

            release()
            wait(3.0)
            telemetry.addLine("grab")
            telemetry.update()
            grab()
            wait(3.0)
            telemetry.addLine("release")
            telemetry.update()
            release()
        }
        if (Config == -2) {

            drive.followTrajectory(tempConfig)
            drive.followTrajectory(tempConfig2)

            telemetry.addData("inVelocity", innerShooterMotor.velocity)
            telemetry.addData("outVelocity", outerShooterMotor.velocity)

            outerShooterMotor.velocity = Velocity.toDouble()
            innerShooterMotor.velocity = Velocity.toDouble()

            wait(0.5)
            shootRing()
            wait(0.5)
            shootRing()
            wait(0.5)
            shootRing()
            wait(0.5)
            shootRing()
            wait(3.0)

            outerShooterMotor.velocity = 0.0
            innerShooterMotor.velocity = 0.0

            drive.followTrajectory(tempConfig3)
            arm.power = 0.4
            wait(0.8)
            arm.power = 0.0
            grabber.position = 0.25

            arm.power = -0.4
            wait(0.7)
            arm.power = 0.0
        }
        if (Config == 0) {
            drive.followTrajectory(Config1Part1)

            innerShooterMotor.velocity = Velocity.toDouble()
            outerShooterMotor.velocity = Velocity.toDouble()

           runPid()

            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()

            innerShooterMotor.velocity = 0.0
            outerShooterMotor.velocity = 0.0

            drive.followTrajectory(Config1Part2)

            release()

            drive.followTrajectory(Config1Part3)

            grab()

            drive.turn(Math.toRadians(-20.0))

            drive.followTrajectory(Config1Part4)

            drive.turn(Math.toRadians(-70.0))

            grabber.position = 0.25
        }
        if (Config == 1) {

            drive.followTrajectory(Config2Part1)

            innerShooterMotor.velocity = Velocity.toDouble()
            outerShooterMotor.velocity = Velocity.toDouble()

            runPid()

            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            innerShooterMotor.velocity = 0.0
            outerShooterMotor.velocity = 0.0

            drive.followTrajectory(Config2Part2)

            release()

            drive.followTrajectory(Config2Part3)

            grab()

            drive.turn(Math.toRadians(-10.0))
            drive.followTrajectory(Config2Part4)
            drive.turn(Math.toRadians(160.0))
            arm.power = -0.7
            wait(0.2)
            arm.power = 0.0
            wait(0.1)
            grabber.position = 0.25
        }
        if (Config == 4) {

            drive.followTrajectory(Config3Part1)

            innerShooterMotor.velocity = Velocity.toDouble()
            outerShooterMotor.velocity = Velocity.toDouble()

//            runPid()

            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            innerShooterMotor.velocity = 0.0
            outerShooterMotor.velocity = 0.0

            drive.followTrajectory(Config3Part2)

            release()

            drive.followTrajectory(Config3Part3a)

            grab()

            drive.followTrajectory(Config3Part4a)

            grabber.position = 0.25

            drive.followTrajectory(Config3Part5)
        }
    }

    val highGoal = BasicHighGoalPipeline(Target.BLUE)

    fun runPid() {
        opencvAssist.webcam.activeCamera = opencvAssist.highCam
        opencvAssist.webcam.setPipeline(highGoal)
        val timer = ElapsedTime()
        while (timer.seconds() < 3) telemetry.addData("centerX", highGoal.blueRect?.centerX())
    }

}
