package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import org.firstinspires.ftc.teamcode.utilities.external.toFixed

class Pose(var position: Point = Point(), var heading: Double = 0.0) {
    constructor(x: Double = 0.0, y: Double = 0.0, heading: Double = 0.0) : this(Point(x, y), heading)

    override fun toString(): String {
        return "Point [$position,heading = ${heading.toFixed(3)}]"
    }

//    override fun equals(other: Any?): Boolean {
//        if (other !is PointImpl<*>) return false
//        return this.x == other.x && this.y == other.y
//    }
//
//    override fun create(x: Double, y: Double): Pose {
//        return Pose(x, y)
//    }
//
//    override fun clone(): Pose = create(x, y)


}

