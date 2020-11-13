package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad

import kotlin.math.atan2
import kotlin.math.hypot

class MOEJoystick(xAxis: () -> Float, yAxis: () -> Float, button: () -> Boolean) {
    val button = MOEButton(button)
    val x = MOEAxis(xAxis)
    val y = MOEAxis(yAxis, true)
    val angle: Double
        get() = atan2(y(), x()) //+ (0)).toDegrees().toNormalAngle()
    val mag: Double
        get() = hypot(x(), y())

    fun update() {
        button.update()
    }
}
