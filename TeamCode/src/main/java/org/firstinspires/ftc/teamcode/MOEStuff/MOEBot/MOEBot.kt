package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.MOEChassis
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEIMUGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEIntake.MOEIntake
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCV
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEShooter.MOEShooter


class MOEBot(config: MOEBotConstantsImpl) {

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
        if (robotConfig.useOpenCV && robotConfig.useVuforia) throw IllegalStateException("Can't use both opencv and vuforia")
        if (robotConfig.useOpenCV) {
            opencv = MOEPenCV(config.getOpenCVConfig())
        } else if (robotConfig.useVuforia) {
            vuforia = MOEVuforia(config.getVuforiaConfig())
        }
    }

    fun offsetValues(constants: MOEBotConstantsImpl) {

        if (robotConfig.useGyro) {
            gyro.angle = constants.getRobotInitialState().robotInitial.radAng
        }


    }

    fun stop() {
//        if (robotConfig.useOdometry) odometry.stop()
        if (robotConfig.useOpenCV) opencv.stop()
    }

}