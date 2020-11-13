package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad

import org.firstinspires.ftc.teamcode.constants.MOEConstants.Gamepad.ON_THRESHOLD

class MOETrigger(trigger: () -> Float) : MOEAxis(trigger) {
    val button = MOEButton { trigger() > ON_THRESHOLD }
    fun update() {
        button.update()
    }

}
