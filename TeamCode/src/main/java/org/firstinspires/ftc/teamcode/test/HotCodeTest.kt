package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode

@Autonomous
class HotCodeTest : OpMode() {
    override fun init() {
    }

    override fun loop() {
        telemetry.addLine("working!!!!")
    }
}