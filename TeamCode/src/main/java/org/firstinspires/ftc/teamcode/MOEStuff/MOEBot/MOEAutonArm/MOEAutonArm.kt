package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEAutonArm

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.AutonArmConfig
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.firstinspires.ftc.teamcode.utilities.internal.wait

class MOEAutonArm(val config: AutonArmConfig) {

    val clawServo = config.getClawServo()
    val armServo = config.getArmServo()
    fun openClaw() {
        clawServo.setPosition(0.0)
    }

    fun closeClaw() {
        clawServo.setPosition(1.0)
    }

    fun raiseArm() {
        armServo.setPosition(0.0)
    }

    fun lowerArm() {
        armServo.setPosition(1.0)
    }

    fun grabStone() {
        openClaw()
        lowerArm()
        moeOpMode.wait(500)
        closeClaw()
        moeOpMode.wait(750)
    }

    fun liftStone() {
        holdStone()
        moeOpMode.wait(500)
    }

    fun holdStone() {
        armServo.setPosition(config.holdStonePosition)
    }

    fun dropStone() {
        lowerArm()
        moeOpMode.wait(100)
        openClaw()
        moeOpMode.wait(500)
    }

    fun initAutonArm() {
        clawServo.setPosition(config.initClawPos)
        armServo.setPosition(config.initArmPos)
    }

}