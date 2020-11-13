package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware

import com.qualcomm.robotcore.hardware.Servo

class ServoConfig(
        name: String,
        hub: Int,
        port: Int,
        val min: Double = 0.0,
        val max: Double = 1.0,
        val direction: Servo.Direction = Servo.Direction.FORWARD
) : MOEConfig<Servo>(name + "S", hub, port)