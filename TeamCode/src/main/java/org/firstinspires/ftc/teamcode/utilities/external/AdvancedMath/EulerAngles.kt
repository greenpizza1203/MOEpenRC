package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath


fun Double.toEulerAngle(): Double {
    // force into the minimum absolute value residue class, so that -180 < angle <= 180
    var angle = toNormalAngle()
    if (angle > 180)
        angle -= 360
    return angle
}

fun Double.toNormalAngle(): Double {
    var angle = this
    // reduce the angle
    angle %= 360

    // force it to be the positive remainder, so that 0 <= angle < 360
    return (angle + 360) % 360
}
