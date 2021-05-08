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
import com.qualcomm.robotcore.hardware.DcMotorSimple
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
class NewAutonomousRed : LinearOpMode() {

    val timer = ElapsedTime()
    fun wait(waitTime: Double) {
        timer.reset()
        while (timer.time() < waitTime && opModeIsActive()) {
        }
    }

    fun shootRing() {
        flickerServo.setPosition(0.0)
        wait(0.4)
        flickerServo.setPosition(0.3)
    }

    fun release() {
        wobbleArmMotor.targetPosition = 200 // diagonal up
        wobbleArmMotor.power = 0.5
        wait(0.5)
        leftWobbleServo.position = 0.76
        rightWobbleServo.position = 0.16
        wait(0.5)
    }

    fun grab() {
        wobbleArmMotor.targetPosition = 320 // all the way down (angle)
        wobbleArmMotor.power = 0.8
        wait(0.5)
        leftWobbleServo.position = 0.16
        rightWobbleServo.position = 0.7
        wait(0.5)
        wobbleArmMotor.targetPosition = 90 // up
        wobbleArmMotor.power = 0.5
    }

    fun end(){
        wait(1.0)
        leftWobbleServo.position = 0.76
        rightWobbleServo.position = 0.16
        wait(0.5)
        wobbleArmMotor.targetPosition = 90
        wobbleArmMotor.power = -0.5
        wait(1.5)
    }

    fun bruh(){
        wobbleArmMotor.targetPosition = 125
        wobbleArmMotor.power = 0.5
    }

    fun shooting(){
//        wait(0.5)
        shooterMotor.velocity = Velocity.toDouble()
//
//        wait(0.5)
//        bruh()
//        wait(1.0)

//        hopperLiftServo.position = 0.8
//        wait(0.5)

        wait(0.15)
        shootRing()
        wait(0.3)
        shootRing()
        wait(0.3)
        shootRing()
//        wait(0.3)
//        shootRing()

        shooterMotor.velocity = 0.0

        hopperLiftServo.position = 0.4
        hopperLiftServo.position = 0.4
    }
     fun lower(){
        wobbleArmMotor.targetPosition = 270
        wobbleArmMotor.power = 0.5
    }
     fun begin(){
        wobbleArmMotor.targetPosition = 200
        wobbleArmMotor.power = 0.1
        hopperLiftServo.position = 0.8

        shooterMotor.velocity = Velocity.toDouble()
    }


    lateinit var frontIntakeMotor: DcMotor
    lateinit var backIntakeMotor: DcMotor
    lateinit var shooterMotor: DcMotorEx
    lateinit var wobbleArmMotor: DcMotorEx

    lateinit var hopperLiftServo: Servo
    lateinit var flickerServo: Servo
    lateinit var leftWobbleServo: Servo
    lateinit var rightWobbleServo: Servo

    val Velocity = 1440
    lateinit var opencvAssist: MOEPipelineAssist

    override fun runOpMode() {
        frontIntakeMotor = hardwareMap.dcMotor["FIM21"]
        frontIntakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        backIntakeMotor = hardwareMap.dcMotor["BIM22"]
        backIntakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        shooterMotor = hardwareMap.get(DcMotorEx::class.java, "RSM10")
        shooterMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        shooterMotor.direction = DcMotorSimple.Direction.REVERSE

        wobbleArmMotor = hardwareMap.get(DcMotorEx::class.java, "WAM11")
        wobbleArmMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        wobbleArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        wobbleArmMotor.targetPosition = 0
        wobbleArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION)

        leftWobbleServo = hardwareMap.servo["LWS20"]
        rightWobbleServo = hardwareMap.servo["RWS11"]
        hopperLiftServo = hardwareMap.servo["HLS10"]
        flickerServo = hardwareMap.servo["FLS21"]

        shooterMotor.setVelocityPIDFCoefficients(100.0, 0.0, 0.0, 13.6)
        wobbleArmMotor.setPositionPIDFCoefficients(11.0)

        for (module in hardwareMap.getAll(LynxModule::class.java)) {
            module.bulkCachingMode = LynxModule.BulkCachingMode.AUTO
        }
//280, 344 // 520 484
        val pipeline = BasicRingPipeline(x = 120, y = 151, width = 105, height = 64)
        opencvAssist = MOEPipelineAssist(hardwareMap, pipeline)

        val drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(-60.0, -48.0, Math.toRadians(0.0))

        drive.poseEstimate = startPose

        val Config1Part1: Trajectory = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(0.0, -36.0), Math.toRadians(0.0))
                .build()

        val Config1Part2: Trajectory = drive.trajectoryBuilder(Config1Part1.end())
                .splineTo(Vector2d(5.0, -45.0), Math.toRadians(100.0))
                .build()

        val Config1Part2b: Trajectory = drive.trajectoryBuilder(Config1Part2.end())
                .forward(12.0)
                .build()

        val Config1Part3: Trajectory = drive.trajectoryBuilder(Config1Part2b.end(), true)
                .splineTo(Vector2d(-34.0, -24.0), Math.toRadians(180.0))
                .build()

        val Config1Part4: Trajectory = drive.trajectoryBuilder(Config1Part3.end(), true)
                .splineTo(Vector2d(0.0, -48.0), Math.toRadians(-40.0))
                .build()

        val Config1Part5: Trajectory = drive.trajectoryBuilder(Config1Part4.end())
                .lineTo(Vector2d(12.0, -12.0))
                .build()

        val Config2Part1: Trajectory = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(-12.0, -56.0), Math.toRadians(0.0))
                .splineTo(Vector2d(0.0, -36.0), Math.toRadians(0.0))
                .build()

        val Config2Part2: Trajectory = drive.trajectoryBuilder(Config2Part1.end())
                .splineToSplineHeading(Pose2d(28.0, -36.0, Math.toRadians(170.0)), Math.toRadians(0.0))
                .build()

        val Config2Part2b: Trajectory = drive.trajectoryBuilder(Config2Part2.end(), true)
                .forward(18.0)
                .build()

        val Config2Part3: Trajectory = drive.trajectoryBuilder(Config2Part2b.end(), true)
                .splineTo(Vector2d(-12.0, -10.0), Math.toRadians(180.0))
                .splineTo(Vector2d(-30.0, -24.0), Math.toRadians(170.0))
                .build()

        val Config2Part3b: Trajectory = drive.trajectoryBuilder(Config2Part3.end())
                .forward(10.0)
                .build()

        val Config2Part4: Trajectory = drive.trajectoryBuilder(Config2Part3b.end(), true)
                .splineTo(Vector2d(22.0, -32.0), Math.toRadians(0.0))
                .build()

        val Config2Part5: Trajectory = drive.trajectoryBuilder(Config1Part4.end())
                .lineTo(Vector2d(8.0, -32.0))
                .build()


        val Config3Part1: Trajectory = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(-12.0, -56.0), Math.toRadians(0.0))
                .splineTo(Vector2d(0.0, -36.0), Math.toRadians(0.0))
                .build()

        val Config3Part2: Trajectory = drive.trajectoryBuilder(Config3Part1.end())
                .splineTo(Vector2d(18.0,-36.0), Math.toRadians(0.0))
                .splineToSplineHeading(Pose2d(46.0, -64.0, Math.toRadians(180.0)), Math.toRadians(0.0))
                .build()

        val Config3Part3: Trajectory = drive.trajectoryBuilder(Config3Part2.end(),true)
                .splineToSplineHeading(Pose2d(-28.0, -24.0, Math.toRadians(0.0)), Math.toRadians(0.0))
                .build()

        val Config3Part3b: Trajectory = drive.trajectoryBuilder(Config3Part3.end(), true)
                .back(14.0)
                .build()

        val Config3Part3c: Trajectory = drive.trajectoryBuilder(Config3Part3b.end(), true)
                .forward(28.0)
                .build()

        val Config3Part4: Trajectory = drive.trajectoryBuilder(Config3Part3c.end())
                .splineToSplineHeading(Pose2d(45.0, -54.0, Math.toRadians(160.0)), Math.toRadians(0.0))
                .build()

        val Config3Part5: Trajectory = drive.trajectoryBuilder(Config3Part4.end())
                .lineTo(Vector2d(12.0, -36.0))
                .build()


//        leftWobbleServo.position = 0.26
//        rightWobbleServo.position = 1.0


        leftWobbleServo.position = 0.16
        rightWobbleServo.position = 0.7
        flickerServo.position = 0.4

        telemetry.addLine("Ready!")
        telemetry.update()

        waitForStart()

        var Config = pipeline.count
        telemetry.addData("RingCount", Config)
        telemetry.update()
        Config = 4
        if (Config == 0) {
            begin()

            drive.followTrajectory(Config1Part1)

            shooting()

            drive.followTrajectory(Config1Part2)

            release()

            drive.followTrajectory(Config1Part2b)
            drive.followTrajectory(Config1Part3)

            grab()

            drive.followTrajectory(Config1Part4)

            release()

            drive.followTrajectory(Config1Part5)
            lower()
        }
        if (Config == 1) {
            begin()
            drive.followTrajectory(Config2Part1)

            shooting()

            drive.followTrajectory(Config2Part2)

            release()

            drive.followTrajectory(Config2Part2b)
            drive.followTrajectory(Config2Part3)

            grab()

            drive.followTrajectory(Config2Part3b)
            drive.followTrajectory(Config2Part4)
            drive.turn(Math.toRadians(-45.0))
            release()
            end()

            drive.followTrajectory(Config2Part5)
            lower()
        }
        if (Config == 4) {
            begin()

            drive.followTrajectory(Config3Part1)

            shooting()

            drive.followTrajectory(Config3Part2)

            release()

            drive.followTrajectory(Config3Part3)
            drive.followTrajectory(Config3Part3b)

            grab()

            drive.followTrajectory(Config3Part3c)
            drive.followTrajectory(Config3Part4)

            release()

            drive.followTrajectory(Config3Part5)

            lower()
        }
    }
//    val highGoal = BasicHighGoalPipeline(Target.BLUE)

//    fun runPid() {
//        opencvAssist.webcam.activeCamera = opencvAssist.highCam
//        opencvAssist.webcam.setPipeline(highGoal)
//        val timer = ElapsedTime()
//        while (timer.seconds() < 3) telemetry.addData("centerX", highGoal.blueRect?.centerX())
//    }
}
