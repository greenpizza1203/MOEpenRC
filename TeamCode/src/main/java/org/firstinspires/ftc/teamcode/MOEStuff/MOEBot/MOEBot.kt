package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.MOEChassis
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.MOERunner
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEBotConstantsImpl
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEIMUGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCV


class MOEBot(val config: MOEBotConstantsImpl) {

    //    val autonArms = MOEAutonArms()
    val robotConfig = config.getRobotSubSystemConfig()


    var chassis = MOEChassis()

    val intake = MOEIntake()
    val shooter = MOEShooter()
    val wobble = MOEWobble()

    lateinit var gyro: MOEGyro
    lateinit var runner: MOERunner
    lateinit var slam: MOESlam
    lateinit var vuforia: MOEVuforia
    lateinit var opencv: MOEPenCV

    init {
        if (robotConfig.useRR) robotConfig.useGyro = true
        if (robotConfig.useGyro) {
            gyro = MOEIMUGyro()
        }
        if (robotConfig.useRR) {
            runner = MOERunner(gyro, chassis)
        }
        if (robotConfig.useSlam) {
            slam = MOESlam()
        }
        if (config.openCVConfig != null && config.vuforiaConfig != null) throw IllegalStateException("Can't use both opencv and vuforia")
        if (config.openCVConfig != null) {
            opencv = MOEPenCV(config.openCVConfig!!)
        } else if (config.vuforiaConfig != null) {
            vuforia = MOEVuforia(config.vuforiaConfig!!)
        }
    }

    fun offsetValues(constants: MOEBotConstantsImpl) {
        if (robotConfig.useGyro) gyro.angle = constants.initialPose.heading

        if (config.openCVConfig != null) opencv.webcam.pauseViewport()
    }

    fun stop() {
        shooter.servoJob.cancel()
        chassis.stop()
        shooter.disable()
        intake.stop()


    }

}