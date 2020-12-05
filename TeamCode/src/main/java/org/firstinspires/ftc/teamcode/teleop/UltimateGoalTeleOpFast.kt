package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
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
        trigger = hardwareMap.servo["RTS25"]
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
        intakeMotor.power = if (aToggled) 1.0 else 0.0
        shooter()
        telemetry.addData("Loop ms", (System.nanoTime() - startTime) / 1000000.0)
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

    var shooterTarget = 2000.0
    private fun shooter() {
        if (yToggled) {
            outerShooterMotor.velocity = shooterTarget
            innerShooterMotor.velocity = shooterTarget
        } else {
            outerShooterMotor.power = 0.0
            innerShooterMotor.power = 0.0
        }
        trigger.position = when {
            timer.time() > 1.75 -> 0.2
            timer.time() > 1.4 -> 0.6
            timer.time() > 1.05 -> 0.2
            timer.time() > 0.7 -> 0.6
            timer.time() > 0.35 -> 0.2
            else -> 0.6
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
    var oldX = false
    fun handleToggles() {
        if (gamepad1.a && !oldA) aToggled = !aToggled
        oldA = gamepad1.a
        if (gamepad1.y && !oldY) yToggled = !yToggled
        oldY = gamepad1.y
        if (gamepad1.x && !oldX) timer.reset()
        oldX = gamepad1.x
    }
}