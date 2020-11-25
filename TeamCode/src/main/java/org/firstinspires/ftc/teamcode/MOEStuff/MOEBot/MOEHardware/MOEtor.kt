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
//        mMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
//        mMotor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        setDirection(config.direction)
        setZeroPowerBehavior(config.zeroPowerBehavior)
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

    fun setDirection(direction: Direction) {
        mMotor.direction = direction
    }

    fun setZeroPowerBehavior(zeroPowerBehavior: ZeroPowerBehavior) {
        mMotor.zeroPowerBehavior = zeroPowerBehavior
    }

    var velocity
        get() = mMotor.velocity
        set(velocity) = mMotor.setVelocity(velocity, RADIANS)

    fun resetEncoder() {
        val oldMOde = mMotor.mode
        mMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        mMotor.mode = oldMOde
    }

    fun setTargetPosition(position: Int) {
        mMotor.targetPosition = position
    }
}
