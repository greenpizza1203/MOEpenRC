package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEtor
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants.DriveTrain.Motors.Configs
import org.firstinspires.ftc.teamcode.constants.Ref.robot
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.PolarPoint


class MOEChassis {

    var frontLeftMotor = MOEtor(Configs.FrontLeft)
    var frontRightMotor = MOEtor(Configs.FrontRight)
    var backLeftMotor = MOEtor(Configs.BackLeft)
    var backRightMotor = MOEtor(Configs.BackRight)
    val motors = listOf(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor)

    fun getFrontVelocities() = Pair(frontLeftMotor.velocity, frontRightMotor.velocity)
    fun getBackVelocities() = Pair(backLeftMotor.velocity, backRightMotor.velocity)

    fun setPower(P: Double) = setPower(P, P)
    fun setVelocity(V: Double) = setVelocity(V, V)

    fun setPower(LP: Double, RP: Double) = setPower(LP, RP, LP, RP)
    fun setVelocity(LV: Double, RV: Double) = setVelocity(LV, RV, LV, RV)

    fun setPower(powers: Powers) = setPower(powers.FLP, powers.FRP, powers.BLP, powers.BRP)
    fun setVelocity(powers: Powers) = setVelocity(powers.FLP, powers.FRP, powers.BLP, powers.BRP)

    fun setPower(FLP: Double, FRP: Double, BLP: Double, BRP: Double) {
        frontLeftMotor.power = (FLP)
        frontRightMotor.power = (FRP)
        backLeftMotor.power = (BLP)
        backRightMotor.power = (BRP)
    }

    fun setVelocity(FLV: Double, FRV: Double, BLV: Double, BRV: Double) {
        frontLeftMotor.velocity = (FLV)
        frontRightMotor.velocity = (FRV)
        backLeftMotor.velocity = (BLV)
        backRightMotor.velocity = (BRV)
    }

    /** LEFT TURNS ARE POSITIVE*/
    fun turnPower(power: Double) = setPower(-power, power)

    fun turnLeftPower(power: Double) = turnPower(-power)
    fun turnRightLeft(power: Double) = turnPower(power)


    fun turnVelocity(vel: Double) = setVelocity(vel, -vel)

    fun setStrafePower(d: Double) {
        robot.chassis.setPower(d, -d, -d, d)
    }

    fun setFromMecanum(fwd: Double, str: Double, rot: Double, maxPower: Double = 1.0) {
        robot.chassis.setPower(Powers.fromMecanum(fwd, str, rot, maxPower))
    }

    fun setFromPolar(vector: PolarPoint, angle: Double, maxPower: Double = 1.0) {
        robot.chassis.setPower(Powers.fromMecanum(vector.x, -vector.y, angle, maxPower))
    }

    fun stop() {
        setPower(0.0)
    }


    //    fun turnTo(degrees: Double) {
//        val pid = MOETurnPid(1.0, 0.0, 0.0)
//        pid.setOutputLimits(1.0)
//        pid.setpoint = degrees
//        pid.input = { robot.gyro.getRawAngle() }
//        pid.output = { robot.chassis.turnPower(it) }
//        pid.run()
//        //        while (moeOpMode.opModeIsActive()) {
//        //            val output = pid.getOutput(robot.gyro.getRawAngle())
//        //            robot.chassis.turnPower(output)
//        //            telemetry.addData(output)
//        //            telemetry.update();
//        //        }
//    }
//    fun moveTo(trans: MOEtion) = pidChassisHandler.moveTo(trans)

//    fun moveTo(x: Double, y: Double, angle: Double) = moveTo(MOEtion(x, y, angle))
//    fun moveTo(x: Double, y: Double) = moveTo(MOEtion(Point(x, y), robot.gyro.angle))

//        val pid = MOEPositionalSystemPid(MOE/Constants.PositionalPid.DefaultOptions)

    fun getVelocities(): List<Double> {
        return motors.map { it.velocity }
    }

    //    fun turn(deg: Double) = pidChassisHandler.turn(deg)
//    fun turnTo(deg: Double) {

//        pidChassisHandler.moveTo(robot.odometry.astarMoetion().apply { degAng = deg.toNormalAngle() })
//    }

    //    fun turn(deg: Double) = pidChassisHandler.moveTo(robot.odometry.astarMoetion().apply {
//        degAng += deg
//        degAng = degAng.toNormalAngle()
//    })


    fun getPowers(): List<Double> {

        return motors.map { it.power }

    }


}
