package org.firstinspires.ftc.teamcode.utilities.external.purepursuit

data class PPOptions(
        val overallMaxVelocity: Double = 0.0,
        val spacing: Double = 0.0,
        val tolerance: Double = 0.0,
        val smoothingA: Double = 0.0,
        val smoothingB: Double = 0.0,
        val turningConstant: Double = 0.0,
        val lookBack: Int = 0,
        val lookForward: Int = 0,
        var lookAheadDistance: Double = 0.0,
        val track_width: Double = 0.0,
        val K_V: Double = 0.0,
        val K_A: Double = 0.0,
        val K_P: Double = 0.0
)
