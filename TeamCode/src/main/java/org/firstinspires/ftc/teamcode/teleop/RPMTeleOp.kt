package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import kotlin.math.abs
import kotlin.math.max
@Disabled
@TeleOp(name = "RPMTeleOp")
class RPMTeleOp : OpMode() {
    lateinit var outerShooterMotor: DcMotorEx
    lateinit var innerShooterMotor: DcMotorEx

    override fun init() {
        outerShooterMotor = hardwareMap.get(DcMotorEx::class.java, "OFM10")
        innerShooterMotor = hardwareMap.get(DcMotorEx::class.java, "IFM11")
    }

    override fun loop() {
//        shooterMotor.power = 1.0
//
//        shooterMotor.velocity
        telemetry.addData("prevY", previousY)
        telemetry.addData("y", gamepad1.y)
        if (!previousY && gamepad1.y) {
            enabled = !enabled
        }
        previousY = gamepad1.y
        if (!enabled) {
            outerShooterMotor.velocity = 0.0
            innerShooterMotor.velocity = 0.0
            return
        }

        outerShooterMotor.velocity = targetVelocity
        innerShooterMotor.velocity = targetVelocity

        targetVelocity += gamepad1.right_trigger.toDouble() * speed
        targetVelocity -= gamepad1.left_trigger.toDouble() * speed

        telemetry.addData("y", gamepad1.y)
        telemetry.addData("enabled", enabled)
        telemetry.addData("outerVelocity", outerShooterMotor.velocity)
        telemetry.addData("innerVelocity", innerShooterMotor.velocity)
        telemetry.addData("targetVelocity", targetVelocity)
        telemetry.addData("right_stick_y", gamepad1.right_stick_y)

    }

    var targetVelocity = 0.0
    val speed = 10

    var enabled = true
    var previousY = false
}







