package org.firstinspires.ftc.teamcode.teleop

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
class UltimateGoalTeleOpFast : OpMode() {
    var gyroOffset = Math.toRadians(90.0)
    lateinit var gyro: BNO055IMU
    lateinit var frontLeftMotor: DcMotor
    lateinit var frontRightMotor: DcMotor
    lateinit var backLeftMotor: DcMotor
    lateinit var backRightMotor: DcMotor
    lateinit var intakeMotor: DcMotor
    lateinit var outerShooterMotor: DcMotorEx
    lateinit var innerShooterMotor: DcMotorEx
    lateinit var trigger: Servo
    lateinit var grabber: Servo
    lateinit var angle: Servo
    lateinit var arm: DcMotor

    var timer = ElapsedTime()
    override fun init() {
        gyro = hardwareMap.get(BNO055IMU::class.java, "imu")
        gyro.initialize(BNO055IMU.Parameters())
        frontLeftMotor = hardwareMap.get(DcMotorEx::class.java, "FLM20")
        frontLeftMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        frontRightMotor = hardwareMap.get(DcMotorEx::class.java, "FRM22")
        frontRightMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        backLeftMotor = hardwareMap.get(DcMotorEx::class.java, "BLM21")
        backLeftMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        backRightMotor = hardwareMap.get(DcMotorEx::class.java, "BRM23")
        backRightMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        frontLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        backLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        intakeMotor = hardwareMap.dcMotor["INM13"]
        intakeMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        outerShooterMotor = hardwareMap.get(DcMotorEx::class.java, "OFM10")
        innerShooterMotor = hardwareMap.get(DcMotorEx::class.java, "IFM11")
        outerShooterMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        innerShooterMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        trigger = hardwareMap.servo["RTS25"]
        grabber = hardwareMap.servo["WAS21"]
        angle = hardwareMap.servo["ACS20"]
        arm = hardwareMap.get(DcMotorEx::class.java, "WAM12")
        arm.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        for (module in hardwareMap.getAll(LynxModule::class.java)) {
            module.bulkCachingMode = LynxModule.BulkCachingMode.AUTO
        }

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
        intakeMotor.power = if (aToggled) 1.0 else if (bToggled) -1.0 else 0.0
        grabber.position = if (rbToggled) 0.4 else 0.05
//        angle.position = if (lbToggled) 260.0 else 0.0
        arm.power = gamepad2.right_stick_y.toDouble() * 0.3
        shooter()
        telemetry.addData("Loop ms", (System.nanoTime() - startTime) / 1000000.0)
        telemetry.addData("innerVelocity", innerShooterMotor.getVelocity())
        telemetry.addData("outerVelocity", outerShooterMotor.getVelocity())

        var position = arm.getCurrentPosition()

        telemetry.addData("arm", position)
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

    var shooterTarget = 2400.0
    var powerShotTarget = 1400.0

    private fun shooter() {
        if (yToggled) {
            if (a2Toggled) {
                trigger.position = when {
                    timer.time() > 0.15 -> 0.2
                    else -> 0.85
                }
                outerShooterMotor.velocity = powerShotTarget
                innerShooterMotor.velocity = powerShotTarget
            } else {
                trigger.position = when {
                    timer.time() > 0.75 -> 0.2
                    timer.time() > 0.6 -> 0.85
                    timer.time() > 0.45 -> 0.2
                    timer.time() > 0.3 -> 0.85
                    timer.time() > 0.15 -> 0.2
                    else -> 0.85
                }
                outerShooterMotor.velocity = shooterTarget
                innerShooterMotor.velocity = shooterTarget
            }
        } else {
            outerShooterMotor.velocity = 0.0
            innerShooterMotor.velocity = 0.0
        }
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
    }
}