package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad

open class MOEAxis(val axis: () -> Float, negate: Boolean = false) {
    val sign = if (negate) -1.0 else 1.0
    operator fun invoke(): Double {
        return sign * axis()
    }
}
