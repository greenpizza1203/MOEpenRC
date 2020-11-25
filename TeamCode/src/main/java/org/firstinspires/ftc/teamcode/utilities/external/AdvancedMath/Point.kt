package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import org.firstinspires.ftc.teamcode.utilities.external.toFixed

class Point(override var x: Double, override var y: Double) : PointImpl<Point> {
    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())

    override fun toString(): String {
        return "Point [x=${x.toFixed(3)}, y=${y.toFixed(3)}]"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is PointImpl<*>) return false
        return this.x == other.x && this.y == other.y
    }

    override fun create(x: Double, y: Double): Point {
        return Point(x, y)
    }

    override fun clone(): Point = create(x, y)


}

