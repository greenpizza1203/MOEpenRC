package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import com.qualcomm.robotcore.hardware.DigitalChannel.Mode.INPUT
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.SwitchConfig

class MOESwitch(val config: SwitchConfig) {
    private val channel = config.getDevice()

    init {
        channel.mode = INPUT
    }

    /**
     * state    flipped    result
     * 0        0           0
     * 1        0           1
     * 0        1           1
     * 1        1           0
     */
    val isPressed: Boolean
        get() = channel.state xor config.flipped
}