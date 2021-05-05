package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@Disabled
@TeleOp
class MotorTest : OpMode() {
    lateinit var motors: List<DcMotor>
    lateinit var frontLeft: DcMotor
    private lateinit var frontRight: DcMotor
    lateinit var backRight: DcMotor
    override fun init() {
//        frontLeft = hardwareMap.dcMotor["FLDM10"]
//        frontLeft.direction = REVERSE
//        frontRight = hardwareMap.dcMotor["BLDM11"]
//        frontRight.direction = REVERSE
       backRight = hardwareMap.dcMotor["BRDM12"]
//        frontRight = hardwareMap.dcMotor["FRDM13"]
//        motors = listOf(frontLeft, frontRight, backLeft, backRight)
    }

    override fun loop() {
        backRight.power = 1.0
//        motors.forEach { it.power = 1.0 }

    }
}