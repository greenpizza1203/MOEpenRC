package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEShooter

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOECRServo
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEma3
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants

class MOETrigger {
    val servo = MOECRServo(MOEHardwareConstants.Shooter.TriggerServo)
    val encoder = MOEma3(MOEHardwareConstants.Shooter.TriggerEncoder)

    var target = 0.0
    fun rotate(radians: Double) {
        target += radians
    }

    fun update() {
        servo.power = if (encoder.position < target) 0.5 else 0.0
    }
}