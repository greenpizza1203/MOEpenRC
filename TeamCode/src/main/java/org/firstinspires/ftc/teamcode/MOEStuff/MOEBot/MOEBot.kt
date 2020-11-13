package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEAutonArm.MOEAutonArms
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.MOEChassis
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEIMUGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEdometryGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEIntake
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEOuttake
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOESkystoneLift
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCV
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEdometry.MOEdometrySystem


class MOEBot(config: MOEBotConstantsImpl) {
    lateinit var autonArms: MOEAutonArms

    //    val autonArms = MOEAutonArms()
    val robotConfig = config.getRobotSubSystemConfig()
    lateinit var lift: MOESkystoneLift
    lateinit var foundation: MOEFoundation
    lateinit var outtake: MOEOuttake
    lateinit var intake: MOEIntake
    lateinit var odometry: MOEdometrySystem

    //    val lift = MOESkystoneLift()
//    val foundation = MOEFoundation()
//    val outtake = MOEOuttake()
    var chassis: MOEChassis = MOEChassis()

    //    var intake: MOEIntake = MOEIntake()
//    var odometry = MOEdometrySystem(config)
    lateinit var gyro: MOEGyro
    lateinit var vuforia: MOEVuforia
    lateinit var opencv: MOEPenCV

    init {

        if (robotConfig.useGyro) {
            gyro = if (robotConfig.useOdometry) MOEdometryGyro(config) else MOEIMUGyro()
        }
        if (robotConfig.useOpenCV && robotConfig.useVuforia) throw IllegalStateException("Can't use both opencv and vuforia")
        if (robotConfig.useOpenCV) {
            opencv = MOEPenCV(config.getOpenCVConfig())
        } else if (robotConfig.useVuforia) {
            vuforia = MOEVuforia(config.getVuforiaConfig())
        }
    }

    fun offsetValues(constants: MOEBotConstantsImpl) {

        if (robotConfig.useOdometry) {
//            odometry.resetValues()
//            odometry.launchLoop()
        }
        if (robotConfig.useGyro) {
            gyro.config = constants.getGyroConfig()
        }


    }

    fun stop() {
//        if (robotConfig.useOdometry) odometry.stop()
        if (robotConfig.useOpenCV) opencv.stop()
    }

    //    fun resetValues() {
    //        if (useSlam)
    //            slam.resetValues(thetaOffset)
    //        gyro.setToZero()
    //    }
}