package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.Ma3Config
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.WrapperHandler
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

class MOEma3(config: Ma3Config) {
    private var mEncoder = config.getDevice()
    private val wrappedValue = WrapperHandler(config.maxValue) { mEncoder.voltage * config.scalar }
    val position
        get() = wrappedValue.getValue()

}