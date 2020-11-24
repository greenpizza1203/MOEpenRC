package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


data class Powers(var FLP: Double, var FRP: Double, var BLP: Double, var BRP: Double) {

    constructor(powers: List<Double>) : this(powers[0], powers[1], powers[2], powers[3])
    constructor(powers: Array<Double>) : this(powers[0], powers[1], powers[2], powers[3])

    companion object {

        fun fromMechanum(fwd: Double, str: Double, rot: Double, maxPower: Double = 1.0): Powers {
            val flp = fwd + str + rot
            val frp = fwd - str - rot
            val blp = fwd - str + rot
            val brp = fwd + str - rot
            var powers = listOf(flp, frp, blp, brp)
            val highestPower = abs(powers.maxBy { abs(it) }!!)
            if (highestPower > maxPower) {
                powers = powers.map { it * (maxPower / highestPower) }
            }
            return Powers(powers)
        }

        fun fromAng(degAng: Double, magnitude: Double, rot: Double, maxPower: Double = 1.0) = fromRadAngle(degAng.toRadians(), magnitude, rot, maxPower)
        private fun fromRadAngle(radAng: Double, magnitude: Double, rot: Double, maxPower: Double = 1.0): Powers {
            return fromMechanum(magnitude * sin(radAng), magnitude * cos(radAng), rot, maxPower)
        }
    }


    operator fun timesAssign(num: Double) {
        FLP *= num
        FRP *= num
        BLP *= num
        BRP *= num
    }

}

