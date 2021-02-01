package org.firstinspires.ftc.teamcode.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer
import com.acmerobotics.roadrunner.util.Angle
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

@TeleOp
open class UltimateGoalTeleOp : MOETeleOp() {

    override fun initOpMode() {
        gpad1.right.stick.button.onKeyDown { robot.gyro.angle = 90.toRadians() }
        initChassis()
        robot.intake.loop {
            if (gpad1.a.isToggled) run() else if (gpad1.b.isPressed) reverse() else stop()
        }
        robot.shooter.loop {
            if (gpad1.y.isToggled) enable() else disable()
            if (gpad2.a.isToggled) target(1400.0) else target(1900.0)
        }
        gpad1.x.onKeyDown {
            if (gpad2.a.isToggled) robot.shooter.shootRing() else robot.shooter.shootRings()
        }
        robot.wobble.loop {
            when {
                gpad2.dpad.up() -> raise()
                gpad2.dpad.down() -> lower()
                else -> stop()
            }
            if (gpad2.dpad.right.isToggled) close() else open()

        }

    }

    open fun initChassis() {
        robot.chassis.loop {
            val driveVector = gpad1.left.stick.vector()
            telemetry.addData("chassis", driveVector)
            //Field centric driving
            driveVector.rotate(-robot.gyro.angle)

            setFromPolar(driveVector, gpad1.right.stick.x())
        }
    }

    override fun mainLoop() {
        telemetry.addData("Shooter mode", if (gpad2.a.isToggled) "Powershot" else "High goal")
        telemetry.addData("innerVelocity", robot.shooter.inner.velocity)
        telemetry.addData("outerVelocity", robot.shooter.outer.velocity)

    }


}
//
//private var lastWheelPositions = emptyList<Double>()
//private var lastHeading = Double.NaN
//var positions = listOf(0.0, 0.0, 100.0, 0.0, 100.0, 100.0)
//fun main() {
//    val localizer = object : TwoTrackingWheelLocalizer(listOf(Pose2d(), Pose2d(heading = 90.0.toRadians()))) {
//        override fun getHeading(): Double {
//            return 0.0
//        }
//
//        var index = 0
//        override fun getWheelPositions(): List<Double> {
//            return listOf(0.0, 0.0)
//        }
//
//        override fun getWheelVelocities(): List<Double> {
//            val subList = positions.subList(index * 2, (index * 2) + 2)
//            index += 1
//            return subList
//
//        }
//
//        override fun getHeadingVelocity(): Double {
//            return 90.0.toRadians()
//        }
//
//    }
//    repeat(positions.size / 2) {
//        localizer.update()
//        println(localizer.poseVelocity)
//    }
//
////    val wheelPositions = getWheelPositions()
////    val heading = getHeading()
////    if (lastWheelPositions.isNotEmpty()) {
////        val wheelDeltas = wheelPositions
////                .zip(lastWheelPositions)
////                .map { it.first - it.second }
////        val headingDelta = Angle.normDelta(heading - lastHeading)
////        val robotPoseDelta = calculatePoseDelta(wheelDeltas, headingDelta)
////    }
////    lastWheelPositions = wheelPositions
////    lastHeading = heading
//}
//
////fun getWheelPositions(): List<Double> {
////    return listOf(0.0, 0.0)
////}
////
////fun getHeading(): Double {
////    return 0.0
////}