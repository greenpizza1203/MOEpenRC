package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware

import com.qualcomm.robotcore.hardware.DigitalChannel

class SwitchConfig(name: String, hub: Int, port: Int, val flipped: Boolean) : MOEConfig<DigitalChannel>(name, hub, port)