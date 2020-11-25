package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.AnalogInput
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

@TeleOp
open class UltimateGoalTeleOp : MOETeleOp() {

    override fun initOpMode() {
        addListeners()
        joystickDrive()
        intake()
        shooter()
    }

    private fun intake() {
        gpad1.a.onKeyDown { robot.intake.run() }
        gpad1.a.onKeyUp { robot.intake.stop() }
    }

    private val targetVelocity = 4000.0
    private fun shooter() {
//        robot.shooter.trigger.loop { update() }
//        gpad1.x.onKeyDown { robot.shooter.shootRing() }
        gpad1.a.onKeyDown { robot.shooter.target(targetVelocity) }
        gpad1.a.onKeyUp { robot.intake.stop() }
    }

    private fun joystickDrive() {
        robot.chassis.loop {
            val polar = gpad1.left.stick.vector()
            polar.rotate(-robot.gyro.angle)
            setFromPolar(polar, gpad1.right.stick.x())
        }
    }

    private fun addListeners() {
        gpad1.y.onKeyDown { robot.gyro.setTo(90.0.toRadians()) }
    }

    override fun mainLoop() {
        telemetry.addData("gyro", robot.gyro.angle)
//        telemetry.addData("ma3Wrapped", robot.shooter.trigger.encoder.position)
//        telemetry.addData("triggerTarget", robot.shooter.trigger.target)
        telemetry.addData("FL,FR,BL,BR", robot.chassis.motors.map { it.power })
        telemetry.addData("intake_motor", robot.intake.motor.power)
        telemetry.addData("innerVelocity", robot.shooter.inner.velocity)
        telemetry.addData("outerVelocity", robot.shooter.outer.velocity)
    }


}