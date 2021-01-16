package org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Point
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.PointImpl

class PPPoint(override var x: Double, override var y: Double, var isCriticalPoint: Boolean = false) : PointImpl<PPPoint> {
    var prefixDistance: Double = 0.0
    var velocity: Double = 0.0
    //    var isCriticalPoint: Boolean = false
    //    var magnitude: Double = 0.0

    constructor(point: Point) : this(point.x, point.y)

    override fun toString(): String = "Point [x=$x, y=$y, velocity=$velocity]"


    companion object {

    }

    override fun create(x: Double, y: Double): PPPoint = PPPoint(x, y, isCriticalPoint)
//    fun create(x: Double, y: Double): PurePursuitPoint {
//        return PurePursuitPoint(x, y, isCriticalPoint)
//    }

    override fun clone(): PPPoint = create(x, y)
    operator fun rangeTo(b: PPPoint) = LineSegment(this, b)
    fun distanceTo(point: PPPoint) = kotlin.math.hypot(this.x - point.x, this.y - point.y)

    fun normalize() = divAssign(hypot())

}
