package org.firstinspires.ftc.teamcode.autonomous.sideconfig

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.Direction
import org.firstinspires.ftc.teamcode.autonomous.vision.SkyStoneLocation

data class Movement(val direction: Direction, val distance: Double, val power: Double?)
data class MovementOptions(var direction: Direction, var left: Double, var middle: Double, val right: Double, val power: Double? = null) {
    fun getDistance(stoneLocation: SkyStoneLocation): Movement {
        val distance = when (stoneLocation) {
            SkyStoneLocation.LEFT -> left
            SkyStoneLocation.MIDDLE -> middle
            SkyStoneLocation.RIGHT -> right
        }
        return Movement(direction, distance, power)
    }

    fun createReflectedMovement(): MovementOptions {
        val newDirection = when (direction) {
            Direction.Left -> Direction.Right
            Direction.Right -> Direction.Left
            else -> direction
        }

        if (direction == Direction.Right) left *= 0.9
        if (direction == Direction.Right) middle *= 0.9
        if (direction == Direction.Right) left *= 0.9
        return MovementOptions(newDirection, left, middle, right, power)
    }

    constructor(direction: Direction, allTogether: Double, power: Double? = null) : this(direction, allTogether, allTogether, allTogether, power)
}
