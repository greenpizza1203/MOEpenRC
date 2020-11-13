package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEBotSubSystemConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEOpenCVConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.autonomous.sideconfig.AutonSideConstants

@Autonomous
class RedAutonSetup : MOETeleOp() {
    override fun mainLoop() {}


    override fun getRobotSubSystemConfig(): MOEBotSubSystemConfig {
        return super.getRobotSubSystemConfig().apply {
            useOpenCV = true
            useGyro = false
        }
    }


    override fun getOpenCVConfig(): MOEOpenCVConfig {
        return super.getOpenCVConfig().apply { enablePreview = true; processExtra = true; drawOverlay = true; autonConfig = AutonSideConstants.red }
    }
}