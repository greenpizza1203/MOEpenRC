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
@TeleOp(name = "MotorPower")
class MotorPower : OpMode() {
    lateinit var frontLeft: DcMotor
    lateinit var frontRight: DcMotor
    lateinit var backLeft: DcMotor
    lateinit var backRight: DcMotor

    override fun init() {
    frontLeft = hardwareMap.dcMotor["leftFront"]
    backLeft = hardwareMap.dcMotor["leftRear"]
    frontRight = hardwareMap.dcMotor["rightFront"]
    backRight = hardwareMap.dcMotor["rightRear"]
    }

    override fun loop() {
        frontLeft.power = 0.25
        backLeft.power = 0.25
        frontRight.power = 0.25
        backRight.power = 0.25
    }
}






