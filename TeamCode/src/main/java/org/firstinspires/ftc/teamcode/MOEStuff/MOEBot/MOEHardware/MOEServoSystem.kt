package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.ServoConfig

open class MOEServoSystem(vararg configs: ServoConfig) {
    var servos = configs.map { MOEServo(it) }

    fun setPosition(position: Double) = servos.forEach { it.setPosition(position) }
}
