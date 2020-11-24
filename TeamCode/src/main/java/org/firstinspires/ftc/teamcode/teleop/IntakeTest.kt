package org.firstinspires.ftc.teamcode.teleop


import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "IntakeTest")
class IntakeTest : OpMode() {
    lateinit var intakeMotor: DcMotorEx

    override fun init() {
        intakeMotor = hardwareMap.dcMotor["RIM"] as DcMotorEx
    }

    override fun loop() {
        telemetry.addData("prevA", previousA)
        telemetry.addData("a", gamepad1.a)
        if (!previousA && gamepad1.a) {
            enabled = !enabled
        }
        previousA = gamepad1.a
        if (!enabled) {
            intakeMotor.power = 0.4
            return
        }
            intakeMotor.power = 0.0
        telemetry.addData("a", gamepad1.a)
        telemetry.addData("enabled", enabled)
        telemetry.addData("intakeMotor", intakeMotor.velocity)

    }


    var enabled = true
    var previousA = false
}
