package org.firstinspires.ftc.teamcode.constants

import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.CRServoConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.Ma3Config
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.MotorConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.ServoConfig


object MOEHardwareConstants {

    object DriveTrain {
        object Motors {
            object Configs {
                val FrontLeft = MotorConfig("FL", 2, 0, REVERSE)
                val BackLeft = MotorConfig("BL", 2, 1, REVERSE)
                val FrontRight = MotorConfig("FR", 2, 2)
                val BackRight = MotorConfig("BR", 2, 3)
            }
        }
    }

    object Intake {
        val IntakeMotor = MotorConfig("IN", 1, 0)
    }

    object Shooter {
        val OuterFlywheel = MotorConfig("OF", 1, 0)
        val InnerFlywheel = MotorConfig("IF", 1, 1)
        val TriggerServo = ServoConfig("ST", 1, 1)
//        val TriggerEncoder = Ma3Config("ST", 1, 0)

    }
}

