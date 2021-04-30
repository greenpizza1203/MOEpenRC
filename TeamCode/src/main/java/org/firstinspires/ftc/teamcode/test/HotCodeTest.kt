package org.firstinspires.ftc.teamcode.test

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode

@Disabled
@Autonomous
class HotCodeTest : OpMode() {
    override fun init() {
    }

    override fun loop() {
        telemetry.addLine("working!!!!")
    }
}