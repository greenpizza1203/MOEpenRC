package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import kotlin.math.PI
const val INCHES_IN_METER=39.3701

fun Double.closestAngleDifference(other: Double) = (other - this + 540.0.toRadians()) % 360.0.toRadians() - 180.0.toRadians()


fun ClosedFloatingPointRange<Double>.lerp(t: Double): Double {
    return this.start + t * (this.endInclusive - this.start)
}


fun Double.toDegrees() = Math.toDegrees(this)
fun Double.toRadians() = Math.toRadians(this)
fun Int.toDegrees() = Math.toDegrees(this.toDouble())
fun Int.toRadians() = Math.toRadians(this.toDouble())
