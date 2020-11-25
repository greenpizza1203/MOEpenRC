package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.CRServoConfig
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.lerp

class MOECRServo(config: CRServoConfig) {
    private var mServo = config.getDevice()

    init {
        mServo.direction = config.direction
    }

    private val powRange = 0.0..config.maxPower

    var power: Double
        get() = mServo.power
        set(value) {
            mServo.power = powRange.lerp(value)
        }


}
