package org.firstinspires.ftc.teamcode.autonomous.vision

import android.graphics.Color

data class HSV(val H: Float, val S: Float, val V: Float) {
    constructor(hsv: FloatArray) : this(hsv[0], hsv[1], hsv[2])
}

fun Int.toHSV(): HSV {
    val hsv = FloatArray(3)
    Color.colorToHSV(this, hsv)
    return HSV(hsv)
}