package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import org.firstinspires.ftc.teamcode.autonomous.sideconfig.AutonSideConfig
import org.firstinspires.ftc.teamcode.autonomous.sideconfig.AutonSideConstants

data class MOEOpenCVConfig(
        var resolution: Resolution = Resolution(800, 448),
        var useInternalCamera: Boolean = false,
        var enablePreview: Boolean = true,
        var drawOverlay: Boolean = false,
        var processExtra: Boolean = false,
        var autonConfig: AutonSideConfig = AutonSideConstants.blue
)