package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.Direction.*
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
import org.firstinspires.ftc.teamcode.autonomous.sideconfig.Movement
import org.firstinspires.ftc.teamcode.constants.MOEConstants.Units.TICS_PER_INCH
import org.firstinspires.ftc.teamcode.constants.MOEPidConstants
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.firstinspires.ftc.teamcode.constants.Ref.robot
import kotlin.math.abs


val defaultVerticalPower = 0.8
val defaultHorizontalPower = 0.5

enum class Direction {
    Forward, Backwards, Left, Right, Vertical, Horizontal;

    fun isVerticalDirection(): Boolean {
        return this == Vertical || this == Forward || this == Backwards
    }


}

class MOEChassisEncoder(val chassis: MOEChassis) {
    var tPid = MOETurnPid(MOEPidConstants.tOptions)

    private fun waitThenStop(motor: DcMotor, target: Double) {
        while (abs(motor.currentPosition - target) > 100)
            chassis.stop()
    }

    private fun setTargetPositionAndPower(motor: DcMotor, position: Int, power: Double) {
        while (motor.isBusy) {
        }
        motor.targetPosition = position
        motor.power = power
    }

    fun move(options: Movement) {
        move(options.direction, options.distance, options.power)
    }

    private fun move(direction: Direction, inches: Double, power: Double? = null) {
        //negate if opposite direction
//        val newInches = if (direction == Direction.Left || direction == Direction.Backwards) -inches else inches
        when (direction) {
            Vertical -> moveVertical(inches, power ?: defaultVerticalPower)
            Horizontal -> moveHorizontal(inches, power ?: defaultHorizontalPower)
            Forward -> moveForwardInches(inches, power ?: defaultVerticalPower)
            Backwards -> moveBackwardInches(inches, power ?: defaultVerticalPower)
            Right -> moveRightInches(inches, power ?: defaultHorizontalPower)
            Left -> moveLeftInches(inches, power ?: defaultHorizontalPower)
        }

    }


    fun moveVertical(inches: Double, power: Double = defaultVerticalPower, holdAngle: Double = robot.gyro.angle) {
        if (inches > 0) moveForwardInches(inches, power, holdAngle) else moveBackwardInches(-inches, power, holdAngle)
    }

    fun moveHorizontal(inches: Double, power: Double = defaultVerticalPower) {
        if (inches > 0) moveRightInches(inches, power) else moveLeftInches(-inches, power)
    }

    fun moveForwardInches(inches: Double, power: Double = defaultVerticalPower, holdAngle: Double = robot.gyro.angle) {
        val weakPower = power * 0.5
        val wheel = robot.odometry.rightForwardWheel

        val final = wheel.getValue() + inches * TICS_PER_INCH
        tPid.setpoint = { holdAngle }
        tPid.reset()
        while (wheel.getValue() < final && moeOpMode.iOpModeIsActive()) {
            val anglePower = tPid.getOutput(robot.gyro.angle)
            val curPower = if (abs(final - wheel.getValue()) < 24 * TICS_PER_INCH) weakPower else power
            robot.chassis.setPower(Powers.fromMechanum(curPower, 0.0, anglePower))
        }
        robot.chassis.stop()

    }

    fun moveBackwardInches(inches: Double, power: Double = defaultVerticalPower, holdAngle: Double = robot.gyro.angle) {
        val weakPower = power * 0.5

        val wheel = robot.odometry.rightForwardWheel

        val final = wheel.getValue() - inches * TICS_PER_INCH
        tPid.setpoint = { holdAngle }
        tPid.reset()

        while (wheel.getValue() > final && moeOpMode.iOpModeIsActive()) {
            val anglePower = tPid.getOutput(robot.gyro.angle)
            val curPower = if (abs(final - wheel.getValue()) < 24 * TICS_PER_INCH) weakPower else power
            robot.chassis.setPower(Powers.fromMechanum(-curPower, 0.0, anglePower))
        }

        robot.chassis.stop()
    }

    fun moveRightInches(inches: Double, power: Double = 0.5) {
        robot.chassis.setStrafePower(power)
        val wheel = robot.odometry.strafeWheel

        val final = wheel.getValue() + inches * TICS_PER_INCH
        while (wheel.getValue() < final && moeOpMode.iOpModeIsActive()) {
        }

        robot.chassis.stop()
    }

    fun moveLeftInches(inches: Double, power: Double = 0.5) {
        if (inches < 0) throw IllegalStateException("you're an idiot")

        robot.chassis.setStrafePower(-power)
        val wheel = robot.odometry.strafeWheel

        val final = wheel.getValue() - inches * TICS_PER_INCH
        while (wheel.getValue() > final && moeOpMode.iOpModeIsActive()) {
//            telemetry.addData("cur inches", wheel.getValue())
//            telemetry.addData("final inches", final)
//            telemetry.update()
        }

        robot.chassis.stop()
    }


}
