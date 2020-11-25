package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple

open class MotorConfig(
        name: String,
        hub: Int,
        port: Int,
        val direction: DcMotorSimple.Direction = DcMotorSimple.Direction.FORWARD,
        val zeroPowerBehavior: DcMotor.ZeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE,
        val maxPow: Double = 1.0
) : MOEConfig<DcMotorEx>((name + "M"), hub, port)