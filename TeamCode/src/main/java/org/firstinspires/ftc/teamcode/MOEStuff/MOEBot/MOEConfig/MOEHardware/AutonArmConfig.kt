package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEServo

class AutonArmConfig(val armServoConfig: ServoConfig, val clawServoConfig: ServoConfig, val initArmPos: Double, val initClawPos: Double, val holdStonePosition: Double
) {

    fun getArmServo() = MOEServo(armServoConfig)
    fun getClawServo() = MOEServo(clawServoConfig)

}