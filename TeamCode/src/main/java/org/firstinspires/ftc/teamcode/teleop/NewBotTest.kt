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
@TeleOp(name = "NewBotTest")
class NewBotTest: OpMode() {
    lateinit var shooter: DcMotorEx
    lateinit var servoA: CRServo
    lateinit var servoB: CRServo

    override fun init(){
        shooter = hardwareMap.get(DcMotorEx::class.java, "shooter")
        servoA = hardwareMap.crservo["servo1"]
        servoB = hardwareMap.crservo["servo2"]
    }

    override fun loop(){
        if (!previousY && gamepad1.y) {
            enabled = !enabled
        }
        previousY = gamepad1.y
        if (!enabled) {
            shooter.velocity = 0.0
            return
        }
        shooter.velocity = targetVelocity

        targetVelocity += gamepad1.right_trigger.toDouble() * speed
        targetVelocity -= gamepad1.left_trigger.toDouble() * speed

        servoA.power = gamepad1.left_stick_y.toDouble() * 0.4
        servoB.power = gamepad1.left_stick_y.toDouble() * -0.4

        servoA.power = power
        servoB.power = power

        telemetry.addData("Velocity", shooter.velocity)
    }
    var power = 0.0

    var targetVelocity = 0.0
    val speed = 10

    var enabled = true
    var previousY = false
}