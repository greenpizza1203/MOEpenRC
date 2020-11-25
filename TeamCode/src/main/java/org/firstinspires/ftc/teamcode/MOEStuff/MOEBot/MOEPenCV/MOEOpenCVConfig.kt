package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV


data class MOEOpenCVConfig(
        var resolution: Resolution = Resolution(800, 448),
        var useInternalCamera: Boolean = false,
        var enablePreview: Boolean = true,
        var drawOverlay: Boolean = false,
        var processExtra: Boolean = false
)