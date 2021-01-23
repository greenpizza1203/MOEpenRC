package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEIntake

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEtor
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants

class MOEIntake {
    var motor = MOEtor(MOEHardwareConstants.Intake.IntakeMotor)

    fun run(power:Double = 1.0) {
        motor.power = power
    }

    fun reverse(power:Double = -1.0){
        motor.power = power
    }
    
    fun stop() {
        motor.power = 0.0

    }
}