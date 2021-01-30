package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


data class Powers(var FLP: Double, var FRP: Double, var BLP: Double, var BRP: Double) {

    constructor(powers: List<Double>) : this(powers[0], powers[1], powers[2], powers[3])

}

