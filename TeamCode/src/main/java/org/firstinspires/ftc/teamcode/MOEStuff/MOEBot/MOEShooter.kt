package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEServo
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEtor
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants

class MOEShooter {


    val outer = MOEtor(MOEHardwareConstants.Shooter.OuterFlywheel)
    val inner = MOEtor(MOEHardwareConstants.Shooter.InnerFlywheel)
    val servo = MOEServo(MOEHardwareConstants.Shooter.TriggerServo)

    //    private val targetVelocity = 2000.0
    var enabled = false

    init {
        servo.setPosition(0.3)
    }

    fun enable() {
        enabled = true
        target()
    }

    private var lastTarget = 2300.0

    fun target(target: Double = lastTarget) {
        lastTarget = target
        val actualVel = if (enabled) target else 0.0
        outer.velocity = actualVel
        inner.velocity = actualVel
    }

    fun disable() {
        enabled = false
        target()
    }

    // time it takes for servo to extend out from rest
    private val extendDuration = 150L

    // gap before extending servo out again
    private val resetGap = 150L
    var servoJob: Job = GlobalScope.launch { }
    fun shootRings(rings: Int = 3) {
        if (servoJob.isActive) {
            servoJob.cancel()
            servo.setPosition(0.0)
            return
        }
        servoJob = GlobalScope.launch {
            repeat(rings) {
                servo.setPosition(1.0)
                delay(extendDuration)
                servo.setPosition(0.0)
                delay(resetGap)
            }
        }
    }

    fun shootRing() {
        shootRings(1)
    }

//    fun shootRing() = trigger.rotate(180.toRadians())


}

