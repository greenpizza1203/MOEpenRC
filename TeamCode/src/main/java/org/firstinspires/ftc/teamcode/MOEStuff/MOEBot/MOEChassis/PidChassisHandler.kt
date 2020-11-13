package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
import org.firstinspires.ftc.teamcode.constants.MOEPidConstants
import org.firstinspires.ftc.teamcode.constants.Ref.robot

class PidChassisHandler {
    private val pid = MOETurnPid(MOEPidConstants.PositionalPid.DefaultOptions.turnOptions)

    init {
        pid.input = {
            robot.gyro.angle
        }

        pid.output = { power ->
            robot.chassis.turnPower(power)
        }
        pid.setOutputLimits(0.8)

//        pid.pids.forEach { it.setOutputLimits(0.5) }
    }

//    fun moveTo(goal: MOEtion): Job {
//        pid.reset()
//        pid.setpoint = { goal }
//        return pid.run()
//    }

    //    fun moveTo(x: Double, y: Double, angle: Double = robot.gyro.angle) = moveTo(MOEtion(x, y, angle))
    fun turnTo(i: Double) {
        pid.setpoint = { i }
        pid.reset()
        pid.run()
    }

}
