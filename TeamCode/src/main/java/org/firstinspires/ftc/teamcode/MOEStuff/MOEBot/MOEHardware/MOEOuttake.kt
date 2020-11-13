package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.OutTakeSystem.Servos.Configs

class MOEOuttake {
    val capstoneServo = MOEServo(Configs.CapstoneServo)
    val grabServo = MOEServo(Configs.GrabberServo)
    val outtakeServo = MOEServo(Configs.OuttakeServo)


    fun grab() {
        grabServo.setPosition(1.0)
    }

    fun release() {
        grabServo.setPosition(0.4)
    }

    fun moveIn() {
        outtakeServo.setPosition(0.0)
    }

    fun moveOut() {
        outtakeServo.setPosition(1.0)
    }
}
