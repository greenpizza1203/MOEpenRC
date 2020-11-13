package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton

@Disabled
@Autonomous
class FakeEncoderBlueAuton : MOEAuton() {
    override fun run() {
        robot.foundation.moveUp()
        robot.chassis.encoders.moveForwardInches(5.0, 0.6)
        robot.chassis.encoders.moveLeftInches(5.0, 0.6)
        robot.chassis.encoders.moveForwardInches(5.0, 0.6)
        robot.foundation.moveDown()
        robot.chassis.encoders.moveBackwardInches(9.0, 0.6)
    }


}