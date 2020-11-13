package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import kotlin.math.PI


fun Double.closestAngleDifference(other: Double) = (other - this + 540) % 360 - 180


fun ClosedFloatingPointRange<Double>.lerp(t: Double): Double {
    return this.start + t * (this.endInclusive - this.start)
}


fun Double.toDegrees() = this * 180 / PI
fun Double.toRadians() = this / 180.0 * PI
