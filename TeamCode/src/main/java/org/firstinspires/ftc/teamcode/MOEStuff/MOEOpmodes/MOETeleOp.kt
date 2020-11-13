package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes

import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad.MOEGamepad


abstract class MOETeleOp : MOELoopedOpMode() {
    lateinit var gpad1: MOEGamepad
    lateinit var gpad2: MOEGamepad
    final override fun moeInternalInit() {
        setUpJoysticks()
        if (getRobotSubSystemConfig().useGyro) {
            robot.gyro.init()
        }
    }

    override fun initLoop(): Boolean {
        if (!robot.robotConfig.useGyro) return true
        telemetry.addData("Initializing", "gyro")
        return robot.gyro.initFinished()
    }

    private fun setUpJoysticks() {
        gpad1 = MOEGamepad(gamepad1)
        gpad2 = MOEGamepad(gamepad2)

    }

    final override fun moeInternalPostInit() {
//        telemetry.addData("Gryo", LOADING_ICON)
//        telemetry.update()
//        if (getRobotSubSystemConfig().useGyro) {
//            robot.gyro.init(true)
//        }
//        telemetry.addData("Gryo", LOADING_ICON)
    }

    override fun internalLoop() {
        gpad1.update()
        gpad2.update()
        mainLoop()
    }

    abstract fun mainLoop()
}
