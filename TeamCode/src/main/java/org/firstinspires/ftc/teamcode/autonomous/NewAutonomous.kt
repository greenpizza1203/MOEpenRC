package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.lynx.LynxModule
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
class NewAutonomous : LinearOpMode() {

    val timer = ElapsedTime()
    fun wait(waitTime: Double) {
        timer.reset()
        while (timer.time() < waitTime && opModeIsActive()) {
        }
    }

    fun shootRing() {
        flickerServo.setPosition(0.85)
        wait(0.2)
        flickerServo.setPosition(0.2)
    }

    fun release() {
        wobbleArmMotor.setPower(0.75)
        leftWobbleServo.position = 0.76
        rightWobbleServo.position = 0.16
    }

    fun grab() {
        wobbleArmMotor // all the way down (angle)
        leftWobbleServo.position = 0.26
        rightWobbleServo.position = 1.0
        wobbleArmMotor // up
    }

    lateinit var frontIntakeMotor: DcMotor
    lateinit var backIntakeMotor: DcMotor
    lateinit var shooterMotor: DcMotorEx
    lateinit var wobbleArmMotor: DcMotorEx

    lateinit var hopperLiftServo: Servo
    lateinit var flickerServo: Servo
    lateinit var leftWobbleServo: Servo
    lateinit var rightWobbleServo: Servo

    val Velocity = 2100
    lateinit var opencvAssist: MOEPipelineAssist

    override fun runOpMode() {
        frontIntakeMotor = hardwareMap.dcMotor["FIM10"]
        frontIntakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        backIntakeMotor = hardwareMap.dcMotor["BIM11"]
        backIntakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        shooterMotor = hardwareMap.get(DcMotorEx::class.java, "RSM12")
        shooterMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        wobbleArmMotor = hardwareMap.get(DcMotorEx::class.java, "WAM13")
        wobbleArmMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        wobbleArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        wobbleArmMotor.setTargetPosition(1000)//Down position
        wobbleArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION)

        leftWobbleServo = hardwareMap.servo["LWS10"]
        rightWobbleServo = hardwareMap.servo["RWS11"]
        hopperLiftServo = hardwareMap.servo["HLS13"]
        flickerServo = hardwareMap.servo["FLS14"]
        for (module in hardwareMap.getAll(LynxModule::class.java)) {
            module.bulkCachingMode = LynxModule.BulkCachingMode.AUTO
        }

        val pipeline = BasicRingPipeline(x = 128, y = 202, width = 95, height = 61)
        opencvAssist = MOEPipelineAssist(hardwareMap, pipeline)

        val drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(-60.0, 24.0, Math.toRadians(0.0))

        drive.poseEstimate = startPose

        val Config1Part1: Trajectory = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(0.0, 36.0), Math.toRadians(0.0))
                .build()

        val Config1Part2: Trajectory = drive.trajectoryBuilder(Config1Part1.end())
                .splineTo(Vector2d(12.0, 40.0), Math.toRadians(-75.0))
                .build()

        val Config1Part3: Trajectory = drive.trajectoryBuilder(Config1Part2.end())
                .splineTo(Vector2d(-34.0, 48.0), Math.toRadians(180.0))
                .build()

        val Config1Part4: Trajectory = drive.trajectoryBuilder(Config1Part3.end(), true)
                .splineTo(Vector2d(0.0, 48.0), Math.toRadians(25.0))
                .build()


        val Config2Part1: Trajectory = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(-12.0, 16.0), Math.toRadians(0.0))
                .splineTo(Vector2d(0.0, 36.0), Math.toRadians(0.0))
                .build()

        val Config2Part2: Trajectory = drive.trajectoryBuilder(Config2Part1.end())
                .splineToSplineHeading(Pose2d(20.0, 36.0, Math.toRadians(180.0)), Math.toRadians(0.0))
                .build()

        val Config2Part3: Trajectory = drive.trajectoryBuilder(Config2Part2.end(), true)
                .splineTo(Vector2d(-12.0, 52.0), Math.toRadians(0.0))
                .splineTo(Vector2d(-35.0, 48.0), Math.toRadians(0.0))
                .build()

        val Config2Part4: Trajectory = drive.trajectoryBuilder(Config2Part3.end(), true)
                .splineTo(Vector2d(22.0, 44.0), Math.toRadians(0.0))
                .build()


        val Config3Part1: Trajectory = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(-12.0, 16.0), Math.toRadians(0.0))
                .splineTo(Vector2d(0.0, 36.0), Math.toRadians(0.0))
                .build()

        val Config3Part2: Trajectory = drive.trajectoryBuilder(Config3Part1.end())
                .splineToSplineHeading(Pose2d(42.0, 54.0, Math.toRadians(180.0)), Math.toRadians(0.0))
                .build()

        val Config3Part3: Trajectory = drive.trajectoryBuilder(Config3Part2.end())
                .splineToSplineHeading(Pose2d(-32.0, 48.0, Math.toRadians(180.0)), Math.toRadians(0.0))
                .build()

        val Config3Part4: Trajectory = drive.trajectoryBuilder(Config3Part3.end())
                .splineToSplineHeading(Pose2d(39.0, 54.0, Math.toRadians(180.0)), Math.toRadians(0.0))
                .build()

        val Config3Part5: Trajectory = drive.trajectoryBuilder(Config3Part4.end())
                .lineTo(Vector2d(12.0, 48.0))
                .build()

        leftWobbleServo.position = 0.26
        rightWobbleServo.position = 1.0

        telemetry.addLine("Ready!")
        telemetry.update()
        waitForStart()

        var Config = pipeline.count
        telemetry.addData("RingCount", Config)
        telemetry.update()

        if (Config == 0) {
            drive.followTrajectory(Config1Part1)

            shooterMotor.velocity = Velocity.toDouble()

            runPid()

            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()

            shooterMotor.velocity = 0.0

            drive.followTrajectory(Config1Part2)

            release()

            drive.followTrajectory(Config1Part3)

            grab()

            drive.followTrajectory(Config1Part4)

            leftWobbleServo.position = 0.76
            rightWobbleServo.position = 0.16
        }
        if (Config == 1) {

            drive.followTrajectory(Config2Part1)

            shooterMotor.velocity = Velocity.toDouble()

            runPid()

            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()

            shooterMotor.velocity = 0.0

            drive.followTrajectory(Config2Part2)

            release()

            drive.followTrajectory(Config2Part3)

            grab()

            drive.followTrajectory(Config2Part4)
            drive.turn(165.0)
        }
        if (Config == 4) {

            drive.followTrajectory(Config3Part1)

            shooterMotor.velocity = Velocity.toDouble()

            runPid()

            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()
            wait(0.15)
            shootRing()

            shooterMotor.velocity = 0.0

            drive.followTrajectory(Config3Part2)

            release()

            drive.followTrajectory(Config3Part3)

            grab()

            drive.followTrajectory(Config3Part4)

            leftWobbleServo.position = 0.76
            rightWobbleServo.position = 0.16

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