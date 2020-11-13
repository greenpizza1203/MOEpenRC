package org.firstinspires.ftc.teamcode.teleop

import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.Powers
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEGyroConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.initialHeight
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
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

    private fun initFoundation() {
        robot.foundation.moveUp()
    }


    private fun initLift() {
        robot.lift.resetEncoders()
        robot.lift.setTargetPosition(10)
        robot.lift.setRunToPosition()
        robot.lift.setTargetTolorence(25)
//        robot.lift.setPower(1.0)
        gpad2.dpad.up.onKeyDown {
            val liftCurPos = robot.lift.getAveragePosition()
            if (liftCurPos < initialHeight * .9) {
                robot.lift.moveToInitial()
            } else {
                robot.lift.moveUpSkystones(1.0)
            }
        }
        gpad2.dpad.down.onKeyDown {
            robot.lift.moveDownSkystones(1.0)
        }

        gpad2.a.onKeyDown {
            if (robot.lift.getAveragePosition() >= lastHighest && !gpad2.b.isToggled) {
                lastHighest = robot.lift.getAveragePosition()
                robot.lift.target = 0.0
            } else if (robot.lift.getAveragePosition() < lastHighest) {
                robot.lift.target = lastHighest
            }
        }
        gpad2.y.onKeyDown {
            lastHighest = robot.lift.getAveragePosition()
        }
    }

    private fun addListeners() {
        gpad1.y.onKeyDown {
            robot.gyro.setToZero()
        }
    }

    private fun initOuttake() {
        robot.outtake.grabServo.setPosition(0.6)
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

    var payloadPos = 0.35
    var hitMax = false
    private fun capstone() {
        if (gpad2.back() && !hitMax) {
            payloadPos += .01
        } else if (gpad2.back() && hitMax) {
            payloadPos -= .01
        }
        if (!hitMax && payloadPos >= .65) {
            hitMax = true
        }
        if (hitMax && payloadPos <= 0.0) {
            hitMax = false
        }
        robot.outtake.capstoneServo.setPosition(payloadPos)
    }

    private fun dpadChassis() {
        val scale = 0.3
        var angle = gpad1.dpad.angle() ?: return
        angle += if (robot.gyro.angle in 90.0..270.0) -90 else 90
        val rot = gpad1.right.stick.x()
        robot.chassis.setPower(Powers.fromAng(angle, scale, rot))

    }

    private fun autonArms() {
//        robot.autonArms.left.apply {
//            if (gpad1.dpad.left.isToggled) lowerArm() else raiseArm()
//        }
//        robot.autonArms.right.apply {
//            if (gpad1.dpad.right.isToggled) lowerArm() else raiseArm()
//        }
//        robot.autonArms.apply {
//            if (gpad1.a.isToggled) closeClaws() else this.openClaws()
//        }

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

        robot.chassis.setPower(Powers.fromMechanum(fwd, str, rot, maxPower))
    }

    private fun intake() {
        val outPower = if (gpad1.b()) 0.25 else 0.0
        val harvesterPow = (outPower - gpad1.right.trigger())
        robot.intake.setPower(harvesterPow)
    }

    private fun foundation() {
        robot.foundation.apply {
            if (gpad1.left.bumper()) moveDown() else moveUp()
        }
    }

    //    var target = 0.0
    var lastHighest = 0.0

    //    val heightTol = 0.0
    var liftPower = 1.0
    val intakeHeight = 200
    var liftJoystickSpeed = 45.0

    open fun lift() {
        var target = robot.lift.target

        val up = gpad2.left.stick.y()
        val liftCurPos = robot.lift.getPositions().average().toInt()


        target += up * liftJoystickSpeed
        if (gpad2.left.stick.y() > -0.1) {
            target = target.coerceAtLeast(0.0)
        }
        if (liftCurPos < 0 && (gpad2.left.stick.y() > -.1)) {
            robot.lift.resetEncoders()
        }


        liftPower = when {
            liftCurPos > 50 -> 1.0
            gpad2.left.stick.button() -> 1.0
            liftCurPos in 0..50 -> 0.5
            else -> 0.25
        }


        robot.lift.setPower(liftPower)
        if (gpad1.right.trigger() > .1 && !gpad2.x.isPressed) {
            robot.lift.setTargetPosition(intakeHeight)
        } else {
            robot.lift.setTargetPosition(target.toInt())
        }
        robot.lift.target = target

    }

    open fun outtake() {

        if (gpad2.b.isToggled) {
            telemetry.addData("grab servo", "grabbing")
            robot.outtake.grab()
        } else {
            telemetry.addData("grab servo", "release")

            robot.outtake.release()
        }
        val outtakeCurPos = robot.outtake.outtakeServo.getPosition()
        val outtakeSpeed = .025

        if (gpad2.left.trigger() > .1 && outtakeCurPos > .05) {
            robot.outtake.outtakeServo.setPosition(outtakeCurPos - gpad2.left.trigger() * outtakeSpeed)
        }
        if (gpad2.right.trigger() > .1 && outtakeCurPos < .95) {
            robot.outtake.outtakeServo.setPosition(outtakeCurPos + gpad2.right.trigger() * outtakeSpeed)
        }
        if (gpad2.left.bumper.isPressed) {
            robot.outtake.moveIn()
        } else if (gpad2.right.bumper.isPressed) {
            robot.outtake.moveOut()
        }

    }

    override fun getGyroConfig(): MOEGyroConfig {
        return super.getGyroConfig().apply {
        }
    }


}