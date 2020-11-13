package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.closestAngleDifference

class MOETurnPid(P: Double = 0.0,
                 I: Double = 0.0,
                 D: Double = 0.0,
                 F: Double = 0.0) : MOERawPid(P, I, D, F) {

    constructor(options: MOEPidOptions) : this(options.P, options.I, options.D, options.F)

    init {
        internalPid.getError = { setpoint, actual -> actual.closestAngleDifference(setpoint) }
//        endCondition = { actual, setpoint, output ->
//            abs(internalPid.getError(setpoint, actual)) < 0.5 &&
//        }
    }
}