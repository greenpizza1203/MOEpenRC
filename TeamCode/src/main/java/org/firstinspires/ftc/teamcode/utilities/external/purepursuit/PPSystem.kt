package org.firstinspires.ftc.teamcode.utilities.external.purepursuit

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Pose
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import processing.ppsimulator.options


class PPSystem(points: MutableList<PPPoint>, private val options: PPOptions) {
//    constructor(srcX: Double, srcY: Double, destX: Double, destY: Double, options: PPOptions) :
//            this(arrayListOf(PPPoint(srcX, srcY), PPPoint(destX, destY)), options)

    var path: PPPath = PPPath(points, options)

    init {
        path.injectPoints()
        path.smoothPoints()
        path.setMaxVelocities()
    }

    private var lastLeftVelocity: Double = 0.0
    private var lastRightVelocity: Double = 0.0
    private var lastLookaheadIndex = 0
    fun getWheelVelocities(currentPose: Pose, leftActualVelocity: Double, rightActualVelocity: Double): Pair<Double, Double> {
        val currentPosition = PPPoint(currentPose.position)
        val (newIndex, lookahead) = path.findLookaheadPoint(currentPosition, lastLookaheadIndex)
        lastLookaheadIndex = newIndex

        val curvature = getSignedCurvatureFromLookaheadPoint(
                lookahead = lookahead,
                currPos = currentPosition,
                heading = currentPose.heading,
                lookaheadDistance = options.lookAheadDistance
        )
//        println(path.points)
        val leftTV = getLeftWheelTargetVelocity(targetVelocity = lookahead.velocity, curvature = curvature)
        val rightTV = getRightWheelTargetVelocity(targetVelocity = lookahead.velocity, curvature = curvature)
//        println(leftTV)
        val leftTA = 0.0 //leftTV - lastLeftVelocity
        val rightTA = 0.0 //rightTV - lastRightVelocity

//        lastLeftVelocity = leftTV
//        lastRightVelocity = rightTV

//        val leftFeedforward = (options.K_V * leftTV) + (options.K_A * leftTA)
//        val rightFeedforward = (options.K_V * rightTV) + (options.K_A * rightTA)

        //            leftWheelTargetVelocity = normalizeVelocity(leftWheelTargetVelocity);
        //            rightWheelTargetVelocity = normalizeVelocity(rightWheelTargetVelocity);
        //            double[] velocities = normalizeVelocities(leftWheelTargetVelocity, rightWheelTargetVelocity);
        //            leftWheelTargetVelocity = velocities[0];
        //            rightWheelTargetVelocity = velocities[1];
        //robot.chassis.setVelocities(leftWheelTargetVelocity,rightWheelTargetVelocity);
        //TODO: if (!path.isForward()) {
        //            leftTargetVel = -leftTargetVel;
        //            rightTargetVel = -rightTargetVel;

        //            double leftFeedback = getWheelFeedbackVelocity(leftWheelTargetVelocity,
        //                                                           robot.chassis.getAStarVelocity(robot.chassis.frontLeftMotor));
        //            double rightFeedback = getWheelFeedbackVelocity(rightWheelTargetVelocity,
        //                                                            robot.chassis.getAStarVelocity(robot.chassis.frontRightMotor));

//        val leftFeedback = options.K_P * (leftTV - leftActualVelocity)
//        val rightFeedback = options.K_P * (rightTV - rightActualVelocity)

//        val leftFinalVelocity = leftFeedforward + leftFeedback
//        val rightFinalVelocity = rightFeedforward + rightFeedback
        //TODO: Fins
//        val scaleDown = 0.4

        return Pair(0.0, 0.0)
    }

    private fun getLeftWheelTargetVelocity(targetVelocity: Double, curvature: Double): Double {
        return targetVelocity * (2 + curvature * options.track_width) / 2
    }

    private fun getRightWheelTargetVelocity(targetVelocity: Double, curvature: Double): Double {
        return targetVelocity * (2 - curvature * options.track_width) / 2
    }
}

fun main() {
    val points = mutableListOf(PPPoint(0.0, 0.0), PPPoint(20.0, 0.0))
    val system = PPSystem(points, options)
    system.getWheelVelocities(Pose(0.0, 0.0, 0.0), 0.0, 0.0)
//    system.getWheelVelocities()
}


