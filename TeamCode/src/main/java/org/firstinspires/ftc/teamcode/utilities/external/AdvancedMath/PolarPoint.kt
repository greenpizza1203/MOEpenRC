package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

data class PolarPoint(val r: Double, val degAng: Double) {
    val radAng: Double
        get() = degAng.toRadians()
}