package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.IntakeSystem.Motors.Configs

class MOEIntake {
    private var leftIntakeMotor = MOEtor(Configs.LeftIntake)
    var rightIntakeMotor = MOEtor(Configs.RightIntake)

    fun setPower(P: Double) = setPower(P, P)
    fun setVelocity(V: Double) = setVelocity(V, V)

    fun setPower(LP: Double, RP: Double) {
        leftIntakeMotor.setPower(LP)
        rightIntakeMotor.setPower(RP)
    }

    private fun setVelocity(LV: Double, RV: Double) {
        leftIntakeMotor.setPower(LV)
        rightIntakeMotor.setPower(RV)
    }
}
