package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.Powers
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.PolarPoint
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.lerp
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@TeleOp
open class CompTeleOp : MOETeleOp() {
    override fun initOpMode() {
        addListeners()
//        Log.e("voltage", hardwareMap.voltageSensor["Expansion Hub 2"].voltage.toString())
    }


    private fun addListeners() {
        gpad1.y.onKeyDown {
            robot.gyro.setToZero()
        }
    }


    //    var oldTime = 0L
    override fun mainLoop() {
        joystickDrive()
        dpadChassis()
//        intake()
//        foundation()
//        lift()
//        outtake()
//        capstone()
        log()
    }


    private fun dpadChassis() {
        val scale = 0.3
        var angle = gpad1.dpad.angle() ?: return
        angle += if (robot.gyro.angle in 90.0..270.0) -90 else 90
        val rot = gpad1.right.stick.x()
        robot.chassis.setFromPolar(PolarPoint(angle, scale), rot)

    }


    open fun log() {
        telemetry.addData("Runninge", this::class.simpleName)
        telemetry.addData("motor controolers", hardwareMap.voltageSensor.entrySet())
//        telemetry.addData("lift", robot.lift.target)
//        telemetry.addData("acutal", robot.lift.getPositions().average())
//        telemetry.addData("payloadpos", payloadPos)
//        telemetry.addData("lastHighest", lastHighest)
//        telemetry.addData("lastHighestTol", (robot.lift.getPositions().average() + heightTol))
//        telemetry.addData("switch", robot.lift.limitSwitch.isPressed)
    }

    val minPower = 0.4
    val maxPower = 1.0
    val powerRange = minPower..maxPower
    open fun joystickDrive() {
        val scaleX = 1.0
        val scaleY = 1.0
        val scaleRot = 0.75

        val angle = robot.gyro.angle

        var rawY = gpad1.left.stick.y()
        var rawX = gpad1.left.stick.x()
        var rot = gpad1.right.stick.x()

        var throttle = powerRange.lerp(gpad1.left.trigger())
        if (gpad1.right.trigger() > 0.1) throttle = 0.4
        rawX *= scaleX * throttle
        rawY *= scaleY * throttle
        rot *= scaleRot * throttle

        if (gpad1.left.bumper() && abs(rot) > 0.1) {
            rawY = rawY.coerceAtLeast(0.0)
        }

        val angRad = Math.toRadians(angle)
        val fwd = rawX * sin(angRad) + rawY * cos(angRad)
        val str = rawX * cos(angRad) - rawY * sin(angRad)

        robot.chassis.setPower(Powers.fromMecanum(fwd, str, rot, maxPower))
    }


    //    var target = 0.0
    var lastHighest = 0.0

    //    val heightTol = 0.0
    var liftPower = 1.0
    val intakeHeight = 200
    var liftJoystickSpeed = 45.0





}