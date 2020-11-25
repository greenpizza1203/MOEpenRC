package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

class CRServoConfig(
        name: String,
        hub: Int,
        port: Int,
        val maxPower: Double = 1.0,
        val direction: DcMotorSimple.Direction = DcMotorSimple.Direction.FORWARD
) : MOEConfig<CRServo>(name + "S", hub, port)