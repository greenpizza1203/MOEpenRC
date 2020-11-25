package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import java.lang.Math.PI


fun Double.toEulerAngle(): Double {
    // force into the minimum absolute value residue class, so that -PI < angle <= PI
    var angle = toNormalAngle()
    if (angle > PI) angle -= 2 * PI
    return angle
}

fun Double.toNormalAngle(): Double {
    var angle = this
    // reduce the angle
    angle %= 2 * PI

    // force it to be the positive remainder, so that 0 <= angle < 2PI
    return (angle + 2 * PI) % (2 * PI)
}
