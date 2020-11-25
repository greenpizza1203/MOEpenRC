package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware

import com.qualcomm.robotcore.hardware.AnalogInput
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

class Ma3Config(name: String, hub: Int, port: Int, val maxValue: Double = 5.0, val scalar: Double = 360.toRadians() / maxValue) : MOEConfig<AnalogInput>(name + "E", hub, port)