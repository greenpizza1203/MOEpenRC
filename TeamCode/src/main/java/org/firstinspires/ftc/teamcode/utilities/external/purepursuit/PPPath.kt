package org.firstinspires.ftc.teamcode.utilities.external.purepursuit


import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.getCurvatureOfPoints
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toLineSegmentList
import org.firstinspires.ftc.teamcode.utilities.external.clone
import org.firstinspires.ftc.teamcode.utilities.external.coerceIn
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.getCircleLineIntersection
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt


class PPPath(var points: List<PPPoint>, private val options: PPOptions) {
    constructor(map: Map<Double, Double>, options: PPOptions) : this(map.map { PPPoint(it.key, it.value) }, options)
    //    constructor(originalPoints: List<Point>, options: MOEPurePursuitOptions) :
    //            this(originalPoints.map { it.toPurePursuitPoint() }, options)

    //TODO: add astar

    //    constructor(srcX: Double, srcY: Double, destX: Double, destY: Double, options: MOEPurePursuitOptions) :
    //            this(mutableListOf<>(PurePursuitPoint(srcX, srcY), PurePursuitPoint(destX, destY)), options)
    val originalPoints = points
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
        smoothedPoints = points.clone()
        var change = options.tolerance
        while (change >= options.tolerance) {
            change = 0.0
            for (i in 1 until smoothedPoints.size - 1) repeat(2) { j ->
                val aux = smoothedPoints[i][j]
                smoothedPoints[i][j] += options.smoothingA * (smoothedPoints[i][j] - smoothedPoints[i][j]) +
                        options.smoothingB * (smoothedPoints[i - 1][j] + smoothedPoints[i + 1][j] - 2.0 * smoothedPoints[i][j])
                change += abs(aux - smoothedPoints[i][j])
            }
        }
        points = smoothedPoints
    }

    private fun setMaxVelocities() {
        for (i in 1 until points.size - 1) {
            val curvature = getCurvatureOfPoints(points[i - 1], points[i], points[i + 1])
            //            println("curvature; $curvature")
            points[i].velocity = min(options.overallMaxVelocity, options.turningConstant / curvature)
        }
        points[0].velocity = options.overallMaxVelocity
        points[points.size - 1].velocity = 0.0
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

        return points.minBy { it.distanceTo(currentPoint) }!!
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
            val b = points[i]
            val a = points[i + 1]
            val lookaheadPoint = getLookaheadPointOnSegment(a, b, currentPosition)
            if (lookaheadPoint != null) return Pair(i + 1, lookaheadPoint)
        }



        return Pair(lastIndex, points[lastIndex])
    }

    // -> !=
    operator fun get(index: Int): PPPoint = points[index]

}





