package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.ServoConfig
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.lerp

class MOEServo(config: ServoConfig) {
    private var mServo = config.getDevice()

    init {
        mServo.scaleRange(config.min, config.max)
        mServo.direction=(config.direction)
    }

    fun getPosition(): Double = mServo.position

    fun setPosition(position: Double) {
        mServo.position = position
    }

    fun setPositionOverTime(destPosition: Double, duration: Double, async: Boolean) {
        val initialPosition = getPosition()
        val launch = GlobalScope.launch {
            val time = ElapsedTime()
            val range = initialPosition..destPosition
            while (time.seconds() < duration && moeOpMode.isActive()) {
                setPosition(range.lerp(time.seconds() / duration))
            }
        }

        if (!async) {
            runBlocking { launch.join() }
        }
    }

//    fun setRange(min: Double, max: Double) {
//        mServo.scaleRange(min, max)
//    }
//
//    fun setDirection(direction: Servo.Direction) {
//        mServo.direction = direction
//    }
}
