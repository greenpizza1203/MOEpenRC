package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware

import com.qualcomm.robotcore.hardware.HardwareDevice
import org.firstinspires.ftc.teamcode.constants.Ref.hardwareMap

val devices = mutableMapOf<String, HardwareDevice>()

open class MOEConfig<Type : HardwareDevice>(val name: String, val hub: Int, val port: Int) {

    fun getDevice(): Type {
        val fullName = name + hub + port
        val device = devices.getOrPut(fullName) {
            hardwareMap.get(fullName)
        }
        @Suppress("UNCHECKED_CAST")
        return device as Type
    }
}
