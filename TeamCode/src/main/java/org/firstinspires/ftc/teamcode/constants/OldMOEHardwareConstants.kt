package org.firstinspires.ftc.teamcode.constants

import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.*
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.DriveTrain.Motors.Configs.BackRight
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.IntakeSystem.Motors.Configs.LeftIntake
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.IntakeSystem.Motors.Configs.RightIntake
import com.qualcomm.robotcore.hardware.Servo.Direction as ServoDirection


object OldMOEHardwareConstants {

    object DriveTrain {
        object Motors {
            object Configs {
                val FrontLeft = MotorConfig("FLD", 1, 0, REVERSE)
                val BackLeft = MotorConfig("BLD", 1, 1, REVERSE)
                val BackRight = MotorConfig("BRD", 1, 2)
                val FrontRight = MotorConfig("FRD", 1, 3)
            }
        }
    }

    object IntakeSystem {
        object Motors {
            object Configs {
                val LeftIntake = MotorConfig("LIN", 1, 1, maxPow = 0.9)
                val RightIntake = MotorConfig("RIN", 2, 1, maxPow = 0.9, direction = REVERSE)
            }

//            const val MaxPower = 0.9
        }
    }

    object OutTakeSystem {
        object Servos {
            object Configs {
                val OuttakeServo = ServoConfig("OUT", 1, 2)
                val GrabberServo = ServoConfig("GRB", 1, 4)
                val CapstoneServo = ServoConfig("CAP", 1, 3)
            }
        }
    }

    object FoundationSystem {
        object Servos {
            object Configs {
                val LeftFoundationGrabber = ServoConfig("LFG", 1, 0, 0.0, 0.5, ServoDirection.FORWARD)
                val RightFoundationGrabber = ServoConfig("RFG", 2, 0, 0.5, 1.0, ServoDirection.REVERSE)
            }
        }
    }

    object Lift {
        object Motors {
            val LeftLiftMotor = MotorConfig("LLF", 1, 0, direction = REVERSE)
            val RightLiftMotor = MotorConfig("RLF", 2, 0, direction = REVERSE)
        }

        object Switches {
            val BottomLimit = SwitchConfig("LIMD", 1, 0, true)
        }
    }

    object Odometry {
        val RightForward = OdometryConfig(RightIntake, true)
        val LeftForward = OdometryConfig(LeftIntake, true)
        val Strafe = OdometryConfig(BackRight, true)

    }

    object AutonArms {


        object Configs {
            val LeftArm = ServoConfig("LAA", 1, 1, min = 0.00, max = 1.0, direction = Servo.Direction.REVERSE)
            val LeftClaw = ServoConfig("LAC", 1, 5, min = 0.08, max = 0.65)
            val LeftConfig = AutonArmConfig(LeftArm, LeftClaw, 0.0, 0.73, 0.335)
            val RightArm = ServoConfig("RAA", 2, 1, min = 0.0, max = 1.0, direction = Servo.Direction.FORWARD)
            val RightClaw = ServoConfig("RAC", 2, 5, min = 0.25, max = 1.0, direction = Servo.Direction.REVERSE)
            val RightConfig = AutonArmConfig(RightArm, RightClaw, 0.0, 0.5, 0.0)
        }
    }
}