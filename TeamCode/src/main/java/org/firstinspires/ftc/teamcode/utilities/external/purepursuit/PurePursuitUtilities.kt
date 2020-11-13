package org.firstinspires.ftc.teamcode.utilities.external.purepursuit

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.PointImpl
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
import kotlin.math.*

fun <T> getSignedCurvatureFromLookaheadPoint(lookahead: PointImpl<T>, currPos: PointImpl<T>,
                                             heading: Double, lookaheadDistance: Double): Double {
    var head = heading + 90
    if (head == 90.0 || head == 270.0) {
        head += 0.0001
    }
    head = (head).toRadians()

    val a = -tan(head)
    val b = 1.0
    val c = tan(head) * currPos.x - currPos.y
    val x = abs(a * lookahead.x + b * lookahead.y + c) / sqrt(a.pow(2.0) + b.pow(2))
    val cross = sin(head) * (lookahead.x - currPos.x) - cos(head) * (lookahead.y - currPos.y)
    val side = if (cross > 0) 1.0 else -1.0
    val curvature = 2 * x / lookaheadDistance.pow(2.0)
    return curvature * side
}