package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEdometry

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.OdometryConfig

class MOEDometryWheel(val config: OdometryConfig) {
    val mMotor = config.motorConfig.getDevice()

    fun resetValues() {
        val oldMOde = mMotor.mode
        mMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        mMotor.mode = oldMOde

    }


    fun getValue() = if (config.negate) -mMotor.currentPosition.toDouble() else mMotor.currentPosition.toDouble()
}
