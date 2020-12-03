package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEHardware.MotorConfig
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.lerp


class MOEtor(val config: MotorConfig) {
    var mMotor = config.getDevice()
    val targetIsHigherThanCurrent
        get() = mMotor.targetPosition > mMotor.currentPosition
    val error
        get() = mMotor.targetPosition - mMotor.currentPosition

    //    private var powerScale = config.minPow..config.maxPow
    private val powRange = 0.0..config.maxPow

    init {
        mMotor.direction = config.direction
        mMotor.zeroPowerBehavior = config.zeroPowerBehavior
    }

    val position: Int
        get() = mMotor.currentPosition

    val isBusy: Boolean
        get() = mMotor.isBusy


//    fun getPower(power: Double) {
//        mMotor.power = (powRange).lerp(power)
//    }

    var power: Double
        get() = mMotor.power
        set(value) {
            mMotor.power = (powRange).lerp(value)
        }
//    fun setPower(power: Double) {
//        mMotor.power = (powRange).lerp(power)
//    }

    fun setMode(mode: DcMotor.RunMode) {
        mMotor.mode = mode
    }


    var velocity: Double
        get() = mMotor.velocity
        set(velocity) {
            mMotor.velocity = velocity
        }

    fun resetEncoder() {
        val oldMOde = mMotor.mode
        mMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        mMotor.mode = oldMOde
    }

    fun setTargetPosition(position: Int) {
        mMotor.targetPosition = position
    }
}
