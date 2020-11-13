package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.constants.MOEPidConstants
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.WrapperHandler

@Disabled
@TeleOp
class OdoRawVals : MOETeleOp() {
//    val odo_sd_main = File(Environment.getExternalStorageDirectory().absolutePath + "/forward.txt")
//    val odo_writer = odo_sd_main.printWriter()

    override fun initOpMode() {
        super.initOpMode()
        robot.odometry.resetValues()

//        robot.odometry.launchLoop()
    }



    val angleWrappedb = WrapperHandler(360.0) { robot.gyro.getRawAngle() }
    override fun mainLoop() {
        dpadChassis()
        logOdo()
    }

    val tPid = MOETurnPid(MOEPidConstants.tOptions)
    private fun dpadChassis() {
        val scale = 0.3
        var x = gpad1.right.stick.x()
        if (gpad1.right.bumper.isToggled) x *= 0.3

        val rot = if (gpad1.x.isToggled) tPid.getOutput(robot.gyro.angle) else x
        when {
            gpad1.dpad.up() -> robot.chassis.setFromMecanum(1.0 * scale, 0.0, rot)
            gpad1.dpad.down() -> robot.chassis.setFromMecanum(-1.0 * scale, 0.0, rot)
            gpad1.dpad.left() -> robot.chassis.setFromMecanum(0.0, -1.0 * scale, rot)
            gpad1.dpad.right() -> robot.chassis.setFromMecanum(0.0, 1.0 * scale, rot)
            else -> robot.chassis.setFromMecanum(0.0, 0.0, rot)
        }
    }


    private fun logOdo() {
//        val angle = robot.gyro.angle
        val rightForwardValue = robot.odometry.rightForwardWheel
        val leftForwardWheel = robot.odometry.leftForwardWheel
        val strafe = robot.odometry.strafeWheel
        val wrappedAngle = angleWrappedb.getValue()
        telemetry.addData("rightFoward", rightForwardValue.getValue())
        telemetry.addData("leftForward", leftForwardWheel.getValue())
        telemetry.addData("strafe", strafe.getValue())
        telemetry.addData("gyro", wrappedAngle)
//        odo_writer.println("$angle\t$rightForwardValue\t$strafe")
    }

    override fun stop() {
//        odo_writer.close()
    }
}