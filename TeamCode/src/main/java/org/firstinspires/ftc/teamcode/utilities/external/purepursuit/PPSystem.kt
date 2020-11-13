package org.firstinspires.ftc.teamcode.utilities.external.purepursuit
//
//import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
//
//
//class PPSystem(points: List<PPPoint>, private val options: PPOptions) {
//    //TODO: atars
//    constructor(srcX: Double, srcY: Double, destX: Double, destY: Double, options: PPOptions) :
//            this(arrayListOf(PPPoint(srcX, srcY), PPPoint(destX, destY)), options)
//
//    private var path: PPPath = PPPath(points, options)
//    private var lastLeftVelocity: Double = 0.0
//    private var lastRightVelocity: Double = 0.0
//
//    fun getWheelVelocities(currentPosition: PPPoint,
//                           currentHeading: Double,
//                           leftActualVelocity: Double,
//                           rightActualVelocity: Double): Pair<Double, Double> {
//
//        val closestPoint = path.getClosestPoint(currentPosition)
//
//        val lookaheadPoint = path.findLookaheadPoint(currentPosition, 0)
//
////        val curvature = getSignedCurvatureFromLookaheadPoint(
////                lookahead = lookaheadPoint,
////                currPos = currentPosition,
////                heading = currentHeading,
////                lookaheadDistance = options.lookAheadDistance
////        )
//
////        val leftTV = getLeftWheelTargetVelocity(targetVelocity = closestPoint.velocity, curvature = curvature)
////        val rightTV = getRightWheelTargetVelocity(targetVelocity = closestPoint.velocity, curvature = curvature)
//
//        val leftTA = 0.0 //leftTV - lastLeftVelocity
//        val rightTA = 0.0 //rightTV - lastRightVelocity
//
////        lastLeftVelocity = leftTV
////        lastRightVelocity = rightTV
//
////        val leftFeedforward = (options.K_V * leftTV) + (options.K_A * leftTA)
////        val rightFeedforward = (options.K_V * rightTV) + (options.K_A * rightTA)
//
//        //            leftWheelTargetVelocity = normalizeVelocity(leftWheelTargetVelocity);
//        //            rightWheelTargetVelocity = normalizeVelocity(rightWheelTargetVelocity);
//        //            double[] velocities = normalizeVelocities(leftWheelTargetVelocity, rightWheelTargetVelocity);
//        //            leftWheelTargetVelocity = velocities[0];
//        //            rightWheelTargetVelocity = velocities[1];
//        //robot.chassis.setVelocities(leftWheelTargetVelocity,rightWheelTargetVelocity);
//        //TODO: if (!path.isForward()) {
//        //            leftTargetVel = -leftTargetVel;
//        //            rightTargetVel = -rightTargetVel;
//
//        //            double leftFeedback = getWheelFeedbackVelocity(leftWheelTargetVelocity,
//        //                                                           robot.chassis.getAStarVelocity(robot.chassis.frontLeftMotor));
//        //            double rightFeedback = getWheelFeedbackVelocity(rightWheelTargetVelocity,
//        //                                                            robot.chassis.getAStarVelocity(robot.chassis.frontRightMotor));
//
////        val leftFeedback = options.K_P * (leftTV - leftActualVelocity)
////        val rightFeedback = options.K_P * (rightTV - rightActualVelocity)
//
////        val leftFinalVelocity = leftFeedforward + leftFeedback
////        val rightFinalVelocity = rightFeedforward + rightFeedback
//        //TODO: Fins
////        val scaleDown = 0.4
//
//        return Pair(0.0, 0.0)
//    }
//
//    private fun getLeftWheelTargetVelocity(targetVelocity: Double, curvature: Double): Double {
//        return targetVelocity * (2 + curvature * options.track_width) / 2
//    }
//
//    private fun getRightWheelTargetVelocity(targetVelocity: Double, curvature: Double): Double {
//        return targetVelocity * (2 - curvature * options.track_width) / 2
//    }
//}
//
//
//
