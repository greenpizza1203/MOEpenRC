package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes

import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEGamepad.MOEGamepad


abstract class MOETeleOp : MOELoopedOpMode() {
    lateinit var gpad1: MOEGamepad
    lateinit var gpad2: MOEGamepad
    override fun moeInternalInit() {
        setUpJoysticks()
        if (getRobotSubSystemConfig().useGyro) {
            robot.gyro.init(false)
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

    override fun internalLoop() {
        gpad1.update()
        gpad2.update()
        mainLoop()
        handleLoops()

    }

    private fun handleLoops() {
        for (func in loops) func.second(func.first)
    }

    private val loops = mutableListOf<Pair<Any, Any.() -> Unit>>()
    fun <T : Any> T.loop(func: T.() -> Unit) {
        loops.add(Pair(this, func as Any.() -> Unit))
    }

    open fun mainLoop() {}
}
