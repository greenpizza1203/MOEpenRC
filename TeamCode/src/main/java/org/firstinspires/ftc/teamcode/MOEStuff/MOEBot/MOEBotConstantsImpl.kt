package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEBotSubSystemConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEVuforiaConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEOpenCVConfig
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.MOEtion

interface MOEBotConstantsImpl {
    fun getRobotSubSystemConfig(): MOEBotSubSystemConfig {
        return MOEBotSubSystemConfig()
    }

    fun createRobot(): MOEBot {
        return MOEBot(this)
    }


    fun getRobotInitialState(): MOEtion {
        return MOEtion()
    }

//    fun getGyroConfig(): MOEGyroConfig {
//        return MOEGyroConfig(getRobotInitialState().robotInitial.radAng)
//    }

    fun getVuforiaConfig(): MOEVuforiaConfig {
        return MOEVuforiaConfig()
    }

    fun getOpenCVConfig(): MOEOpenCVConfig {
        return MOEOpenCVConfig()
    }


}
