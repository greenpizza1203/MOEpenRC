package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad

class MOEGamepadSide(stick_x: () -> Float, stick_y: () -> Float, trigger: () -> Float, bumper: () -> Boolean, stick_button: () -> Boolean) {
    val stick = MOEJoystick(stick_x, stick_y, stick_button)
    val trigger = MOETrigger(trigger)
    val bumper = MOEButton(bumper)

    fun update() {
        stick.update()
        trigger.update()
        bumper.update()
    }
}
