package org.firstinspires.ftc.teamcode.utilities.external.purepursuit


import android.R.attr.path
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Point
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.getCurvatureOfPoints
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toLineSegmentList
import org.firstinspires.ftc.teamcode.utilities.external.clone
import org.firstinspires.ftc.teamcode.utilities.external.coerceIn
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.getCircleLineIntersection
import org.firstinspires.ftc.teamcode.utilities.external.zipThree
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt


class PPPath(var points: MutableList<PPPoint>, private val options: PPOptions) {
    //    constructor(points: List<PPPoint>, options: PPOptions) : this(points.toMutableList(), options)
    constructor(map: Map<Double, Double>, options: PPOptions) : this(map.map { PPPoint(it.key, it.value) }.toMutableList(), options)
    //    constructor(originalPoints: List<Point>, options: MOEPurePursuitOptions) :
    //            this(originalPoints.map { it.toPurePursuitPoint() }, options)

    //TODO: add astar

    //    constructor(srcX: Double, srcY: Double, destX: Double, destY: Double, options: MOEPurePursuitOptions) :
    //            this(mutableListOf<>(PurePursuitPoint(srcX, srcY), PurePursuitPoint(destX, destY)), options)
    val originalPoints = points.clone()
    lateinit var injectedPoints: MutableList<PPPoint>
    private lateinit var smoothedPoints: List<PPPoint>
//
//    init {
//        injectPoints()
//        smoothPoints()
//        //                setMaxVelocities()
//        //                smoothVelocities()
//        points.forEach { it.velocity = options.overallMaxVelocity }
//    }


    fun injectPoints() {
        //        val newPathing = ArrayList<PurePursuitPoint>()
        val segments = points.toLineSegmentList()
        val injectedMulti = segments.map { it.injectPoints(options.spacing) }

        injectedMulti.forEach { it.first().isCriticalPoint = true }
        injectedPoints = injectedMulti.flatten().toMutableList()
        points.last().apply {
            isCriticalPoint = true
            injectedPoints.add(this)
        }
        //        lastPoint.isCriticalPoint = true
        //        injectedMulti.add(lastPoint)


        //        this.injectedPoints = injectedMulti
        this.points = injectedPoints
    }

    fun smoothPoints() {
        //copy array
        //copy array
        val weight_data = options.smoothingB
        val weight_smooth = options.smoothingA
        val tolerance = options.smoothingTolerance
        val newPath: List<PPPoint> = points.clone()

        do {
            var change = 0.0
            for (i in 1 until points.size - 1) for (j in 0 until 2) {
                val aux = newPath[i][j]
                newPath[i][j] += weight_data * (points[i][j] - newPath[i][j]) + weight_smooth * (newPath[i - 1][j] + newPath[i + 1][j] - 2.0 * newPath[i][j])
                change += abs(aux - newPath[i][j])
            }
        } while (change >= tolerance)

        points = newPath.toMutableList()
    }

    fun setMaxVelocities() {
        points.zipThree { a, b, c ->
            val curvature = getCurvatureOfPoints(a, b, c)
            b.velocity = min(options.overallMaxVelocity, options.turningConstant / curvature)
            println(b.velocity)
        }
//        for (i in 1 until points.size - 1) {
//            val curvature = getCurvatureOfPoints(points[i - 1], points[i], points[i + 1])
//            points[i].velocity = min(options.overallMaxVelocity, options.turningConstant / curvature)
////            println("curvature; $curvature")
//        }
        points.first().velocity = options.overallMaxVelocity
        points.last().velocity = 0.0
    }

    private fun smoothVelocities() {
        var velocity: Double
        points[points.size - 1].velocity = 0.0
        for (i in points.size - 2 downTo 0) {
            val nextVelocity = points[i + 1].velocity
            val a = options.overallMaxVelocity
            val distance = points[i].distanceTo(points[i + 1])
            val newVelocity = sqrt(nextVelocity.pow(2.0) + 2.0 * a * distance)
            velocity = min(points[i].velocity, newVelocity)
            points[i].velocity = velocity
        }
    }

    fun getClosestPoint(currentPoint: PPPoint): PPPoint {
//        val searchIndexes = ((lastKnownPointIndex - options.lookBack)..(lastKnownPointIndex + options.lookForward)).coerceIn(points.indices)

        return points.minByOrNull { it.distanceTo(currentPoint) }!!
        //        for (i in (lastKnownPointIndex - options.lookBack)..lastKnownPointIndex + options.lookForward) {
        //            if (i >= 0 && i < points.size) {
        //                val dist = points[i].distanceFrom(currentPoint)
        //                if (dist < closestDistance) {
        //                    closestDistance = dist
        //                    closestPointIndex = i
        //                }
        //            }


    }


    private fun getLookaheadPointOnSegment(
            startPoint: PPPoint,
            endPoint: PPPoint,
            currentPosition: PPPoint
    ): PPPoint? {
        val progress = getCircleLineIntersection(startPoint, endPoint, currentPosition, options.lookAheadDistance)
        if (progress.isNaN()) return null
        val intersectVector = endPoint - startPoint
        val vectorSegment = intersectVector * progress
        return startPoint + vectorSegment
    }


    fun findLookaheadPoint(currentPosition: PPPoint, lastIndex: Int, lookBack: Int = options.lookBack, lookForward: Int = options.lookForward): Pair<Int, PPPoint> {
        val searchIndexes = ((lastIndex - lookBack - 1)..(lastIndex + lookForward)).coerceIn(points.indices)
        if (searchIndexes.last == points.lastIndex) {
            val lastPoint = points.last()
            if (currentPosition.distanceTo(lastPoint) < options.lookAheadDistance) return Pair(points.lastIndex, lastPoint)
        }
        for (i in searchIndexes.last - 1 downTo searchIndexes.first) {
            val a = points[i]
            val b = points[i + 1]
            val lookaheadPoint = getLookaheadPointOnSegment(a, b, currentPosition)
            if (lookaheadPoint != null) return Pair(i + 1, lookaheadPoint)
        }

        return Pair(lastIndex, points[lastIndex])
    }

    // -> !=
    operator fun get(index: Int): PPPoint = points[index]

}





