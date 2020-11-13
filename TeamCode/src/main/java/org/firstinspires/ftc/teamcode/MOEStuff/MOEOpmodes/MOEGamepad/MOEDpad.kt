package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad

class MOEDpad(upB: () -> Boolean, downB: () -> Boolean, leftB: () -> Boolean, rightB: () -> Boolean) {
    val up = MOEButton(upB)
    val down = MOEButton(downB)
    val left = MOEButton(leftB)
    val right = MOEButton(rightB)
    private val buttons = arrayOf(up, down, left, right)
    fun update() {
        buttons.forEach { it.update() }
    }

    fun angle(): Double? {
        return when {
            right() -> 0.0
            up() -> 90.0
            left() -> 180.0
            down() -> 270.0
            else -> null
        }
    }
}
