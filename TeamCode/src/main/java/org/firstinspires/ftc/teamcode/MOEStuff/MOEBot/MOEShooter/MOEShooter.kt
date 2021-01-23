package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEShooter

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
    private val targetVelocity = 2000.0

    init {
        servo.setPosition(0.3)
    }

    fun run(target: Double = targetVelocity) {
        outer.velocity = target
        inner.velocity = target
    }

    fun stop() {
        run(0.0)
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

