package org.firstinspires.ftc.teamcode.autonomous.sideconfig

import org.firstinspires.ftc.teamcode.autonomous.vision.SkyStoneLocation
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Rectangle

data class AutonSideConfig(val movements: List<MovementOptions>, val cropRectangle: Rectangle, val negateStuff: Boolean) {
    fun getDistances(skyStoneLocation: SkyStoneLocation): List<Movement> {
        return movements.map { it.getDistance(skyStoneLocation) }
    }

    fun reflect(newCroppedRectangle: Rectangle): AutonSideConfig {
        val newMovments = movements.map { it.createReflectedMovement() }
        return AutonSideConfig(newMovments, newCroppedRectangle, !negateStuff)
    }
}
