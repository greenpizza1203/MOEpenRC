package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEtor
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants.DriveTrain.Motors.Configs
import org.firstinspires.ftc.teamcode.constants.Ref.robot
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.PolarPoint
import org.firstinspires.ftc.teamcode.utilities.external.modify
import org.firstinspires.ftc.teamcode.utilities.external.timesAssign
import kotlin.math.abs


class MOEChassis {

    var frontLeftMotor = MOEtor(Configs.FrontLeft)
    var frontRightMotor = MOEtor(Configs.FrontRight)
    var backLeftMotor = MOEtor(Configs.BackLeft)
    var backRightMotor = MOEtor(Configs.BackRight)
    val motors = listOf(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor)


    fun setPower(P: Double) = setPower(P, P)

    fun setPower(LP: Double, RP: Double) = setPower(LP, RP, LP, RP)

    fun setPower(FLP: Double, FRP: Double, BLP: Double, BRP: Double) {
        frontLeftMotor.power = (FLP)
        frontRightMotor.power = (FRP)
        backLeftMotor.power = (BLP)
        backRightMotor.power = (BRP)
    }


    /** LEFT TURNS ARE POSITIVE*/
    fun turnPower(power: Double) = setPower(-power, power)

//    class Powers(var FLP: Double, var FRP: Double, var BLP: Double, var BRP: Double) {
//        constructor(powers: List<Double>) : this(powers[0], powers[1], powers[2], powers[3])
//    }

    fun setFromMecanum(fwd: Double, str: Double, rot: Double, maxPower: Double = 1.0) {
        val flp = fwd + str + rot
        val frp = fwd - str - rot
        val blp = fwd - str + rot
        val brp = fwd + str - rot
        val powers = mutableListOf(flp, frp, blp, brp)
        val highestPower = powers.maxOf { abs(it) }
        if (highestPower > maxPower) powers *= (maxPower / highestPower)
        setPower(powers[0], powers[1], powers[2], powers[3])
    }

    fun setFromPolar(vector: PolarPoint, angle: Double, maxPower: Double = 1.0) {
        robot.chassis.setFromMecanum(vector.x, -vector.y, angle, maxPower)
    }

    fun stop() {
        setPower(0.0)
    }
//
//
//    fun getPowers(): List<Double> {
//
//        return motors.map { it.power }
//
//    }


}
