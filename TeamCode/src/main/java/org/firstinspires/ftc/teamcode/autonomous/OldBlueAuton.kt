package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton
import org.firstinspires.ftc.teamcode.utilities.internal.waitSeconds

@Disabled
@Autonomous
class OldBlueAuton : MOEAuton() {
    override fun initOpMode() {
        robot.foundation.moveUp()
    }

    override fun run() {
        robot.chassis.setPower(0.2, 0.6, 0.6, 0.2) //forward and slightly left
        waitSeconds(1.9)
        robot.chassis.stop()
        robot.foundation.moveDown()
        waitSeconds(1)
        robot.chassis.setPower(-0.5) //backup
        waitSeconds(2)
        robot.chassis.stop()
        robot.chassis.setPower(-0.5, -0.25, -0.5, 0.25) //turn and slide
        waitSeconds(2.3)
        robot.chassis.stop()
//        robot.foundation.moveUp()
//        waitSeconds(1)
//        robot.chassis.setPower(-0.3) //park
//        waitSeconds(2)
//        robot.chassis.stop()
    }

}