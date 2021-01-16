package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV


data class MOEPenCVConfig(
        var pipeline: MOEPipeline,
        var resolution: Resolution = Resolution(800, 448)
)