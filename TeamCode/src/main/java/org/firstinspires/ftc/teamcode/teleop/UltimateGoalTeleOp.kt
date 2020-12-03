package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

@TeleOp
open class UltimateGoalTeleOp : MOETeleOp() {

    override fun initOpMode() {
        gpad1.right.stick.button.onKeyDown { robot.gyro.angle = 90.toRadians() }
        initChassis()
        robot.intake.loop {
            if (gpad1.a.isToggled) run() else stop()
        }
        robot.shooter.loop {
            if (gpad1.y.isToggled) run() else stop()
        }
        gpad1.x.onKeyDown {
            robot.shooter.shoot()
        }
    }

    open fun initChassis() {
        robot.chassis.loop {
            val driveVector = gpad1.left.stick.vector()

            //Field centric driving
            driveVector.rotate(-robot.gyro.angle)

            setFromPolar(driveVector, gpad1.right.stick.x())
        }
    }

    override fun mainLoop() {
        telemetry.addData("gyro", robot.gyro.angle)
        telemetry.addData("FL,FR,BL,BR", robot.chassis.motors.map { it.power })
        telemetry.addData("intake_motor", robot.intake.motor.power)
        telemetry.addData("innerVelocity", robot.shooter.inner.velocity)
        telemetry.addData("outerVelocity", robot.shooter.outer.velocity)

    }
}