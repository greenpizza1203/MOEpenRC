package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import kotlin.math.cos
import kotlin.math.sin

data class PolarPoint(val r: Double, var radAng: Double) {
    val degAng: Double
        get() = radAng.toDegrees()
    val x: Double
        get() = r * cos(radAng)
    val y: Double
        get() = r * sin(radAng)

    override fun toString(): String {
        return "($r,$degAng°)"
    }

    fun rotate(angle: Double) {
        radAng += angle
    }
}