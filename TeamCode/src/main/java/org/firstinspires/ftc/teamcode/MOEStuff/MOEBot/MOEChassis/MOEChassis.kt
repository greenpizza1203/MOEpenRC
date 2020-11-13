package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEtor
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.DriveTrain.Motors.Configs
import org.firstinspires.ftc.teamcode.constants.Ref.robot
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toNormalAngle


class MOEChassis {
    val pidChassisHandler = PidChassisHandler()

    var frontLeftMotor = MOEtor(Configs.FrontLeft)
    var frontRightMotor = MOEtor(Configs.FrontRight)
    var backLeftMotor = MOEtor(Configs.BackLeft)
    var backRightMotor = MOEtor(Configs.BackRight)
    val motors = listOf(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor)
    val encoders = MOEChassisEncoder(this)

    fun getFrontVelocities() = Pair(frontLeftMotor.getVelocity(), frontRightMotor.getVelocity())
    fun getBackVelocities() = Pair(backLeftMotor.getVelocity(), backRightMotor.getVelocity())

    fun setPower(P: Double) = setPower(P, P)
    fun setVelocity(V: Double) = setVelocity(V, V)

    fun setPower(LP: Double, RP: Double) = setPower(LP, RP, LP, RP)
    fun setVelocity(LV: Double, RV: Double) = setVelocity(LV, RV, LV, RV)

    fun setPower(powers: Powers) = setPower(powers.FLP, powers.FRP, powers.BLP, powers.BRP)
    fun setVelocity(powers: Powers) = setVelocity(powers.FLP, powers.FRP, powers.BLP, powers.BRP)

    fun setPower(FLP: Double, FRP: Double, BLP: Double, BRP: Double) {
        frontLeftMotor.setPower(FLP)
        frontRightMotor.setPower(FRP)
        backLeftMotor.setPower(BLP)
        backRightMotor.setPower(BRP)
    }

    fun setVelocity(FLV: Double, FRV: Double, BLV: Double, BRV: Double) {
        frontLeftMotor.setVelocity(FLV)
        frontRightMotor.setVelocity(FRV)
        backLeftMotor.setVelocity(BLV)
        backRightMotor.setVelocity(BRV)
    }

    fun turnPower(power: Double) = setPower(power, -power)

    fun turnRightPower(power: Double) = turnPower(power)
    fun turnRightLeft(power: Double) = turnPower(-power)


    fun turnVelocity(vel: Double) = setVelocity(vel, -vel)

    fun setStrafePower(d: Double) {
        robot.chassis.setPower(d, -d, -d, d)
    }

    fun setFromMecanum(fwd: Double, str: Double, rot: Double, maxPower: Double = 1.0) {
        robot.chassis.setPower(Powers.fromMechanum(fwd, str, rot, maxPower))
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
        return motors.map { it.getVelocity() }
    }

    //    fun turn(deg: Double) = pidChassisHandler.turn(deg)
//    fun turnTo(deg: Double) {

//        pidChassisHandler.moveTo(robot.odometry.astarMoetion().apply { degAng = deg.toNormalAngle() })
//    }

    //    fun turn(deg: Double) = pidChassisHandler.moveTo(robot.odometry.astarMoetion().apply {
//        degAng += deg
//        degAng = degAng.toNormalAngle()
//    })
    fun turnTo(i: Double) {

        robot.chassis.pidChassisHandler.turnTo(i.toNormalAngle())
    }

}
