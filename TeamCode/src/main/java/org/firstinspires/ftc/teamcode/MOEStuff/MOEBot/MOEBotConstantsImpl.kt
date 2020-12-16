package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import com.acmerobotics.roadrunner.geometry.Pose2d
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEBotSubSystemConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEVuforiaConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCVConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPipeline

interface MOEBotConstantsImpl {
    val vuforiaConfig: MOEVuforiaConfig?
        get() = null
    val openCVConfig: MOEPenCVConfig?
        get() = null

    fun getRobotSubSystemConfig(): MOEBotSubSystemConfig {
        return MOEBotSubSystemConfig()
    }

    fun createRobot(): MOEBot {
        return MOEBot(this)
    }

    val initialPose
        get() = Pose2d()
//    fun getRobotInitialState(): Pose2d {
//        return Pose2d()
//    }

}
