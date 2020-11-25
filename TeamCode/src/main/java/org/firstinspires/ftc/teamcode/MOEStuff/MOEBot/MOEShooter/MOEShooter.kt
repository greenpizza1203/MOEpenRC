package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEShooter

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEtor
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

class MOEShooter {


    val outer = MOEtor(MOEHardwareConstants.Shooter.OuterFlywheel)
    val inner = MOEtor(MOEHardwareConstants.Shooter.InnerFlywheel)
    val trigger = MOETrigger()

    fun target(target: Double) {
        outer.velocity = target
        inner.velocity = target
    }

    fun stop() {
        outer.velocity = 0.0
        inner.velocity = 0.0

    }

    fun shootRing() = trigger.rotate(180.toRadians())


}

