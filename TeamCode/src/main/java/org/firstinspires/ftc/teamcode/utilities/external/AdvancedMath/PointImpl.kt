package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import org.firstinspires.ftc.teamcode.utilities.external.other.Cloneable

interface PointImpl<T> : Cloneable<T> {
    var x: Double
    var y: Double
    operator fun plus(other: PointImpl<T>): T = create(this.x + other.x, this.y + other.y)
    operator fun minus(other: PointImpl<T>): T = create(this.x - other.x, this.y - other.y)
    operator fun times(value: Double) = create(this.x * value, this.y * value)
    operator fun div(value: Double) = create(this.x / value, this.y / value)
    operator fun unaryMinus() = create(-this.x, -this.y)
    operator fun timesAssign(value: Double) {
        this.x *= value; this.y *= value
    }

    operator fun divAssign(value: Double) {
        this.x /= value; this.y /= value

    }

    /**
     * 0 up
     * 90 right
     */
    fun atan2() = kotlin.math.atan2(x, y)

    fun hypot(): Double = kotlin.math.hypot(x, y)

    infix fun dot(p: PointImpl<T>): Double {
        return x * p.x + y * p.y
    }

    operator fun component1(): Double = x
    operator fun component2(): Double = y

    operator fun get(index: Int): Double {
        return when (index) {
            0 -> x
            1 -> y
            else -> throw IllegalStateException("Point only has two components (x and y)")
        }
    }

    fun create(x: Double, y: Double): T

    //    abstract override clone(): T
    operator fun set(index: Int, value: Double) {
        when (index) {
            0 -> x = value
            1 -> y = value
            else -> throw IllegalStateException("Point only has two components (x and y)")
        }
    }
}
