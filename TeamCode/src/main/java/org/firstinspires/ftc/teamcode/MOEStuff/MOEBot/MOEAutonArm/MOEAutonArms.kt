package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEAutonArm

import org.firstinspires.ftc.teamcode.autonomous.constants.AutonConstants
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants

class MOEAutonArms {
    val left = MOEAutonArm(OldMOEHardwareConstants.AutonArms.Configs.LeftConfig)

    val right = MOEAutonArm(OldMOEHardwareConstants.AutonArms.Configs.RightConfig)
    val arms = listOf(left, right)
    fun openClaws() {
        arms.forEach { it.openClaw() }
    }

    fun closeClaws() {
        arms.forEach { it.closeClaw() }
    }

    fun initAutonArms() {
        arms.forEach { it.initAutonArm() }
    }

    fun moveToShowCamera() {
        left.armServo.setPosition(AutonConstants.OPEN_FOR_CAMERA_SERVO)
    }

    fun raiseArms() {
        arms.forEach { it.raiseArm() }
    }


}