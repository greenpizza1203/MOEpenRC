package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEServo
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEtor
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants.Wobble

class MOEWobble {


    val arm = MOEtor(Wobble.WobbleArm)
    val servo = MOEServo(Wobble.WobbleServo)
    fun open() {
        servo.setPosition(1.0)
    }

    fun close() {
        servo.setPosition(0.0)
    }

    fun raise() {
        arm.power = 1.0
    }

    fun lower() {
        arm.power = -0.5
    }

    fun stop() {
        arm.power = 0.0

    }
}

