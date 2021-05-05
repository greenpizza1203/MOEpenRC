package org.firstinspires.ftc.teamcode.teleop

import com.acmerobotics.roadrunner.control.PIDFController
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

@TeleOp
open class NewTeleop : OpMode() {
    var gyroOffset = Math.toRadians(90.0)
    lateinit var gyro: BNO055IMU

    lateinit var frontLeftMotor: DcMotor
    lateinit var frontRightMotor: DcMotor
    lateinit var backLeftMotor: DcMotor
    lateinit var backRightMotor: DcMotor
    lateinit var frontIntakeMotor: DcMotor
    lateinit var backIntakeMotor: DcMotor
    lateinit var shooterMotor: DcMotorEx
    lateinit var wobbleArmMotor: DcMotorEx

    lateinit var hopperLiftServo: Servo
    lateinit var flickerServo: Servo
    lateinit var leftWobbleServo: Servo
    lateinit var rightWobbleServo: Servo

    var timer = ElapsedTime()
    var timer2 = ElapsedTime()


    override fun init(){
        gyro = hardwareMap.get(BNO055IMU::class.java, "imu")
        gyro.initialize(BNO055IMU.Parameters())

        frontLeftMotor = hardwareMap.get(DcMotorEx::class.java, "FLM20")
        frontLeftMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        frontRightMotor = hardwareMap.get(DcMotorEx::class.java, "FRM12")
        frontRightMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        backLeftMotor = hardwareMap.get(DcMotorEx::class.java, "BLM23")
        backLeftMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        backRightMotor = hardwareMap.get(DcMotorEx::class.java, "BRM13")
        backRightMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE

        frontLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        backLeftMotor.direction = DcMotorSimple.Direction.REVERSE

        frontIntakeMotor = hardwareMap.dcMotor["FIM21"]
        frontIntakeMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        backIntakeMotor = hardwareMap.dcMotor["BIM22"]
        backIntakeMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE

        shooterMotor = hardwareMap.get(DcMotorEx::class.java, "RSM10")
        shooterMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        shooterMotor.direction = DcMotorSimple.Direction.REVERSE
//        shooterMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER

        wobbleArmMotor = hardwareMap.get(DcMotorEx::class.java, "WAM11")
        wobbleArmMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        wobbleArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        wobbleArmMotor.targetPosition = 0
        wobbleArmMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

        leftWobbleServo = hardwareMap.servo["LWS20"]
        rightWobbleServo = hardwareMap.servo["RWS11"]
        hopperLiftServo = hardwareMap.servo["HLS10"]
        flickerServo = hardwareMap.servo["FLS21"]
        for (module in hardwareMap.getAll(LynxModule::class.java)) {
            module.bulkCachingMode = LynxModule.BulkCachingMode.AUTO
        }
//        shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, PIDFCoefficients(0.1, 0.0, 0.0, 0.0))
        shooterMotor.setVelocityPIDFCoefficients(100.0, 0.0, 0.0, 12.6)
//        wobbleArmMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, PIDFCoefficients(10.0, 0.0, 0.0, 3.0))
        wobbleArmMotor.setPositionPIDFCoefficients(11.0)
    }

    override fun init_loop() {
        if (!gyro.isGyroCalibrated) {
            telemetry.addData("Initializing", "Gyro")
        } else {
            telemetry.addData("Initializing", "Complete")
        }
    }

    override fun loop() {
        val startTime = System.nanoTime()
        handleToggles()
        if (gamepad1.right_stick_button) gyroOffset = 90 + gyro.angularOrientation.firstAngle.toDouble()
        loopChassis()
        frontIntakeMotor.power = if (aToggled) 0.6 else if (gamepad1.dpad_down) -1.0 else 0.0
        backIntakeMotor.power = if (aToggled) 0.6 else if (gamepad1.dpad_down) -1.0 else 0.0
        shooter()
        grabber()
        telemetry.addData("Loop ms", (System.nanoTime() - startTime) / 1000000.0)
        telemetry.addData("shooterVelocity", shooterMotor.getVelocity())
        telemetry.addData("state", armState)
        telemetry.addData("timer1", timer.time())
        telemetry.addData("timer2", timer2.time())
        telemetry.addData("y",yToggled)
        telemetry.addData("lift",hopperLiftServo.position)

    }

    fun loopChassis() {
        val y = -gamepad1.left_stick_y.toDouble()
        val x = -gamepad1.left_stick_x.toDouble()
        val rot = gamepad1.right_stick_x.toDouble()
        val angle = gyro.angularOrientation.firstAngle - gyroOffset
        val s = sin(angle)
        val c = cos(angle)
        //Field centric driving
        val fwd = x * c - y * s
        val str = x * s + y * c
        fromMecanum(fwd, str, rot)
    }

    fun fromMecanum(fwd: Double, str: Double, rot: Double) {
        val flp = fwd + str + rot
        val frp = fwd - str - rot
        val blp = fwd - str + rot
        val brp = fwd + str - rot
        val max = max(1.0, max(flp, max(frp, max(blp, brp))))
        frontLeftMotor.power = flp / max
        frontRightMotor.power = frp / max
        backLeftMotor.power = blp / max
        backRightMotor.power = brp / max
    }

    val powerShotTarget = 1700.0
    val shooterTarget = 1450.0
//1700 squishy

    //flicker back .24
    //flicker forward 0.0
    private fun shooter() {
        if (yToggled) {
            hopperLiftServo.position = 0.8
            shooterMotor.velocity = shooterTarget
            if (gamepad1.dpad_up) {
                flickerServo.position = when {
                    timer.time() > 0.55 -> 0.24
                    else -> 0.0
                }
                timer.reset()
            } else {
                flickerServo.position = when {
                    timer.time() > 1.4 -> 0.0
                    timer.time() > 1.0 -> 0.24
                    timer.time() > 0.85 -> 0.0
                    timer.time() > 0.7 -> 0.24
                    timer.time() > 0.55 -> 0.0
                    else -> 0.24
                }
            }
        } else {
            hopperLiftServo.position = 0.0
            flickerServo.position = 0.0
            shooterMotor.velocity = 0.0
        }
    }

    var wgGrab = false
    var wgDrop = false
    var armState = 1 //Up


    private fun grabber() {
//
        if (gamepad1.dpad_right && !oldDpadRight && armState == 1) {
            armState = 2
        } else if (gamepad1.dpad_right && !oldDpadRight && armState == 2) {
            timer2.reset()
            wobbleArmMotor.targetPosition = 300
            wobbleArmMotor.power = 0.3
            wgGrab = true
        } else if (gamepad1.dpad_right && !oldDpadRight && armState == 3) {
            timer2.reset()
            wobbleArmMotor.targetPosition = 200
            wobbleArmMotor.power = 0.2
            wgDrop = true
        }

        if (armState == 1) {
            //UP
            wobbleArmMotor.targetPosition = 90
            wobbleArmMotor.power = 0.3
            leftWobbleServo.position = 0.66
            rightWobbleServo.position = 0.12
        } else if (armState == 2) {
            //DIAGONAL UP
            if (!wgGrab) {
                wobbleArmMotor.targetPosition = 200
                wobbleArmMotor.power = 0.3
                leftWobbleServo.position = 0.66
                rightWobbleServo.position = 0.12
            }
        } else if (armState == 3) {
            //UP WITH WOBBLE GOAL
            if (!wgDrop) {
                wobbleArmMotor.targetPosition = 90
                wobbleArmMotor.power = 0.3
                leftWobbleServo.position = 0.26
                rightWobbleServo.position = 0.7
            }

        }


        if (wgGrab) {
            if (timer2.time() > 0.2) {
                leftWobbleServo.position = 0.26
                rightWobbleServo.position = 0.7
            }
            if (timer2.time() > 1) {
                wgGrab = false
                armState = 3
            }
        }
        if (wgDrop) {
            if (timer2.time() > 0.5) {
                leftWobbleServo.position = 0.66
                rightWobbleServo.position = 0.12
            }
            if (timer2.time() > 1.3) {
                wgDrop = false
                armState = 1
            }
        }
    }



//        if(gamepad1.dpad_right && !oldDpadRight && armState == 1){
//            wobbleArmMotor.targetPosition = 200
//            wobbleArmMotor.power = 0.5
//            leftWobbleServo.position = 0.66
//            rightWobbleServo.position = 0.12
//            armState = 2 // straight
//        } else if(gamepad1.dpad_right && !oldDpadRight && armState == 2){
//            wobbleArmMotor.targetPosition = 320 // diagonal down
//            wobbleArmMotor.power = 0.5
//            timer2.reset()
//            if (timer2.time() > 1.0) {
//                leftWobbleServo.position = 0.26
//                rightWobbleServo.position = 0.7 //Closed
//                timer2.reset()
//            }
//            if (timer2.time() > 0.5) {
//                wobbleArmMotor.targetPosition = 90 // Up
//                wobbleArmMotor.power = 0.5
//            }
//            armState = 3 //Up Closed
//        } else if (gamepad1.dpad_right && !oldDpadRight && armState == 3){
//            wobbleArmMotor.targetPosition = 200 // diagonal up
//            wobbleArmMotor.power = -0.5
//            timer2.reset()
//            if (timer2.time() > 1.0) {
//                leftWobbleServo.position = 0.66
//                rightWobbleServo.position = 0.12 //Open
//            }
//            timer2.reset()
//            if (timer2.time() > 1.0) {
//                wobbleArmMotor.targetPosition = 90// Up
//                wobbleArmMotor.power = 0.5
//            }
//            armState = 1
//        }

    var oldA = false
    var aToggled = false
    var oldY = false
    var yToggled = false
    var oldB = false
    var bToggled = false
    var oldX = false
    var rbToggled = false
    var oldRB = false
    var lbToggled = false
    var oldLB = false
    var a2Toggled = false
    var olda2 = false
    var dpadUpToggled = false
    var oldDpadUp = false
    var dpadDownToggled = false
    var oldDpadDown = false
    var dpadLeftToggled = false
    var oldDpadLeft = false
    var dpadRightToggled = false
    var oldDpadRight = false
    var dpadLeft2Toggled = false
    var oldDpadLeft2 = false
    var oldY2 = false
    var y2Toggled = false
    fun handleToggles() {
        if (gamepad1.a && !oldA) aToggled = !aToggled
        oldA = gamepad1.a
        if (gamepad1.y && !oldY) yToggled = !yToggled
        oldY = gamepad1.y
        if (gamepad1.b && !oldB) bToggled = !bToggled
        oldB = gamepad1.b
        if (gamepad1.right_bumper && !oldRB) rbToggled = !rbToggled
        oldRB = gamepad1.right_bumper
        if (gamepad1.left_bumper && !oldLB) lbToggled = !lbToggled
        oldLB = gamepad1.left_bumper
        if (gamepad2.a && !olda2) a2Toggled = !a2Toggled
        olda2 = gamepad2.a
        if (gamepad1.x && !oldX) timer.reset()
        oldX = gamepad1.x
        if (gamepad1.dpad_up && !oldDpadUp) dpadUpToggled = !dpadUpToggled
        oldDpadUp = gamepad1.dpad_up
        if (gamepad1.dpad_down && !oldDpadDown) dpadDownToggled = !dpadDownToggled
        oldDpadDown = gamepad1.dpad_down
        if (gamepad1.dpad_left && !oldDpadLeft) dpadLeftToggled = !dpadLeftToggled
        oldDpadLeft = gamepad1.dpad_left
        if (gamepad1.dpad_right && !oldDpadRight) dpadRightToggled = !dpadRightToggled
        oldDpadRight = gamepad1.dpad_right
        if (gamepad2.dpad_left && !oldDpadLeft2) dpadLeft2Toggled = !dpadLeft2Toggled
        oldDpadLeft2 = gamepad2.dpad_left
        if (gamepad2.y && !oldY2) y2Toggled = !y2Toggled
        oldY2 = gamepad2.y
    }
}