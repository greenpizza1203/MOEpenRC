package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro

import android.util.Log
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEGyroConfig
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toEulerAngle
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toNormalAngle
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

abstract class MOEGyro {


    var config = MOEGyroConfig()
        set(value) {
            field = value
            setTo(value.initalAng)
        }
    var offset = 0.0
    private var eulerOffset = 0.0

    /** 0 to 2PI*/
    val angle: Double
        get() = (getRawAngle() + offset).toNormalAngle()

    /**
     * Sets an offset for the IMU angle values
     * @param offset the offset to set
     */

    val radAng: Double
        get() = angle.toRadians()

    /**
     * Set the current angle as 0
     */
    fun setToZero() {
        setTo(0.0)
    }

    fun setTo(initialAng: Double) {
//        Log.e("setting gyro to ", initialAng.toString())
        offset = initialAng - getRawAngle()
    }

    /** 0 to 2PI*/
    abstract fun getRawAngle(): Double

    /** -2PI to 2PI*/
    open fun getRawEulerAngle(): Double = getRawAngle().toEulerAngle()

    open fun init(sync: Boolean = true) {}


    var eulerAngle: Double
        get() {
            return getRawEulerAngle() - eulerOffset
        }
        set(it) {
            eulerOffset = getRawEulerAngle() - it
        }

    fun resetEulerAngle() {
        eulerAngle = 0.0
    }

    abstract fun initFinished(): Boolean


}