package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.PolarPoint
import kotlin.math.atan2
import kotlin.math.hypot

class MOEJoystick(xAxis: () -> Float, yAxis: () -> Float, button: () -> Boolean) {
    val button = MOEButton(button)
    val x = MOEAxis(xAxis)
    val y = MOEAxis(yAxis, true)
    val ang get() = atan2(y(), x()) //+ (0)).toDegrees().toNormalAngle()
    val mag get() = hypot(x(), y())
    fun vector() = PolarPoint(mag, ang)
    fun update() {
        button.update()
    }
}
