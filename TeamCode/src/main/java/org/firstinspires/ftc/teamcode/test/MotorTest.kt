package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE


@TeleOp
class MotorTest : OpMode() {
    lateinit var motors: List<DcMotor>
    lateinit var frontLeft: DcMotor
    lateinit var frontRight: DcMotor
    lateinit var backLeft: DcMotor
    lateinit var backRight: DcMotor
    override fun init() {
        frontLeft = hardwareMap.dcMotor["FLDM10"]
        frontLeft.direction = REVERSE
        frontRight = hardwareMap.dcMotor["BLDM11"]
        frontRight.direction = REVERSE

        backLeft = hardwareMap.dcMotor["BRDM12"]
        backRight = hardwareMap.dcMotor["FRDM13"]
        motors = listOf(frontLeft, frontRight, backLeft, backRight)
    }

    override fun loop() {
        motors.forEach { it.power = 1.0 }

    }
}