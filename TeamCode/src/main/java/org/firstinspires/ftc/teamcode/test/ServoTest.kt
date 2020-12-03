package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo


@Autonomous
class ServoTest : OpMode() {
    lateinit var servo0: Servo
    lateinit var servo1: Servo
    lateinit var servo5: Servo
    override fun init() {

        servo0 = hardwareMap.servo["servo0"]
        servo1 = hardwareMap.servo["servo1"]
        servo5 = hardwareMap.servo["servo5"]
    }

    override fun loop() {
        servo0.position = 0.0
        servo1.position = 1.0
        servo5.position = 0.5
    }
}