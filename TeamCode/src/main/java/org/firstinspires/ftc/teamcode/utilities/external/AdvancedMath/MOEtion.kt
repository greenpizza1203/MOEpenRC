package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

data class MOEtion(var pose: Point = Point(0.0, 0.0), var degAng: Double = 0.0) {
    constructor(x: Double, y: Double, degAng: Double) : this(Point(x, y), degAng)

    val radAng
        get() = degAng.toRadians()

    override fun toString(): String {
        return "pose: $pose\n angle:$degAng"
    }

}
