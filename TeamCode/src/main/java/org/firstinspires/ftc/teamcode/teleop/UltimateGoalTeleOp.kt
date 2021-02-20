package org.firstinspires.ftc.teamcode.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
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
            if (gpad2.a.isToggled) target(1900.0) else target(2100.0)
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
        gpad1.back.onKeyDown {
            openCVConfig?.pipeline?.savePicture()
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
    override val initialPose = Pose2d(heading = 270.toRadians())


}
