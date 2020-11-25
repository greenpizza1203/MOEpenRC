package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


/**
 * creates another point relative to this one given distance and angle
 *
 * 0 is straight up
 * 90 is to the right
 * 180 is down
 * 270 is lef
 */
fun Point.getRelativePoint(distanceFromThis: Double, radTheta: Double): Point {
    val cameraX = sin(radTheta) * distanceFromThis
    val cameraY = cos(radTheta) * distanceFromThis

    return Point(this.x + cameraX, this.y + cameraY)
}

///** clockwise rotation */
//fun <T : PPPoint> T.rotateAroundOrigin(radAng: Double): T {
//    val newX = x * cos(radAng) - y * sin(radAng)
//    val newY = x * sin(radAng) + y * cos(radAng)
//    return create(newX, newY)
//}

//    return sqrt((this.x - point.x).pow(2.0) + (this.y - point.y).pow(2.0))

fun Iterable<PPPoint>.toLineSegmentList() = zipWithNext { a, b -> a..b }


fun PPPoint.toPurePursuitPoint() = PPPoint(x, y)

fun getCurvatureOfPoints(leftPoint: PPPoint, centerPoint: PPPoint, rightPoint: PPPoint): Double {
    if (centerPoint.x == 90.0) {
        centerPoint.x += 0.0001
    } //Fixing for DivideByZero error

    val k1 = 0.5 * (centerPoint.x * centerPoint.x + centerPoint.y * centerPoint.y - leftPoint.x * leftPoint.x - leftPoint.y * leftPoint.y) / (centerPoint.x - leftPoint.x)
    val k2 = (centerPoint.y - leftPoint.y) / (centerPoint.x - leftPoint.x)
    val b = 0.5 * (leftPoint.x * leftPoint.x - 2.0 * leftPoint.x * k1 + leftPoint.y * leftPoint.y - rightPoint.x * rightPoint.x + 2.0 * rightPoint.x * k1 - rightPoint.y * rightPoint.y) / (rightPoint.x * k2 - rightPoint.y + leftPoint.y - leftPoint.x * k2)
    val a = k1 - k2 * b
    val r = sqrt((centerPoint.x - a).pow(2.0) + (centerPoint.y - b).pow(2.0))
    val curvature = 1 / r
    return if (curvature.isNaN()) 0.0 else curvature
}