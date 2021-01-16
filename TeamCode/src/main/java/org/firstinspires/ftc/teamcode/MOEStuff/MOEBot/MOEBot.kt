package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.MOEChassis
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEIMUGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEIntake.MOEIntake
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCV
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEShooter.MOEShooter


class MOEBot(val config: MOEBotConstantsImpl) {

    //    val autonArms = MOEAutonArms()
    val robotConfig = config.getRobotSubSystemConfig()


    //    val lift = MOESkystoneLift()
//    val foundation = MOEFoundation()
//    val outtake = MOEOuttake()
    var chassis = MOEChassis()

    val intake = MOEIntake()
    val shooter = MOEShooter()

    //    var intake: MOEIntake = MOEIntake()
//    var odometry = MOEdometrySystem(config)
    lateinit var gyro: MOEGyro
    lateinit var vuforia: MOEVuforia
    lateinit var opencv: MOEPenCV

    init {
        if (robotConfig.useGyro) {
            gyro = MOEIMUGyro()
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
        shooter.stop()
        intake.stop()


    }

}