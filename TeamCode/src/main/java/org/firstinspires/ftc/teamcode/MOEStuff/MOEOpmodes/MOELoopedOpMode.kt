package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBot
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBotConstantsImpl
import org.firstinspires.ftc.teamcode.constants.OpModeInterface
import org.firstinspires.ftc.teamcode.constants.Ref
import org.firstinspires.ftc.teamcode.constants.Ref.setRobotRef
import org.firstinspires.ftc.teamcode.utilities.internal.addData

abstract class MOELoopedOpMode : OpMode(), OpModeInterface, MOEBotConstantsImpl {
    lateinit var robot: MOEBot
    var opModeIsActive: Boolean = false

    @Volatile
    var isStopRequested = false

    override fun iRequestOpModeStop() = requestOpModeStop()

    override fun isActive(): Boolean = opModeIsActive && !iIsStopRequested

    override val iIsStopRequested: Boolean
        get() = isStopRequested

    //    override fun iOpModeIsActive(): Boolean =

    final override fun init() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE)
        opModeIsActive = false
        moeDoubleInternalInit()
        initRobot()
        moeInternalInit()
        setRobotRef(robot)
        initOpMode()
        moeInternalPostInit()

    }

    private fun initRobot() {
        robot = createRobot()
    }

    open fun initLoop(): Boolean {
        return true
    }

    var needToLoop = true
    final override fun init_loop() {
        if (needToLoop) {
            val result = initLoop()
            if (result) needToLoop = false
        } else {
            postInitLoop()
        }
    }

    private fun postInitLoop() {
        notifyInitFinished()
    }

    //    open fun afterInit() {
//        telemetry.update()
//    }
    var firstTime = true
    var currTime = 0L
    override fun loop() {
        if (firstTime) {
            offsetRobotValues()
            firstTime = false
        }
        currTime = System.nanoTime()
        opModeIsActive = true
        internalLoop()
        telemetry.addData("Loop time", (System.nanoTime() - currTime) / 1000000)

    }

    open fun internalLoop() {

    }

    open fun moeInternalPostInit() {

    }


    private fun moeDoubleInternalInit() {
        setRefs()

    }


    private fun setRefs() {
        Ref.setRefs(this)
    }


    private fun notifyInitFinished() {
        telemetry.addData("waiting for start")
        telemetry.update()
    }

    private fun offsetRobotValues() {
        robot.offsetValues(this)
    }

    open fun moeInternalInit() {}

    open fun initOpMode() {}

    override fun stop() {

        isStopRequested = true
        @Suppress("SENSELESS_COMPARISON")
        if (robot != null)
            robot.stop()
//        Log.e("stopped", "stop")
    }
}
