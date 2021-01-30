package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.Ma3Config
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.MotorConfig
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.WrapperHandler
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

class MOEEncoder(config: MotorConfig) {
    var mMotor = config.getDevice()

    fun resetEncoder() {
        val oldMode = mMotor.mode
        mMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        mMotor.mode = oldMode
    }

    val position get() = mMotor.currentPosition
    val velocity get() = mMotor.velocity


}