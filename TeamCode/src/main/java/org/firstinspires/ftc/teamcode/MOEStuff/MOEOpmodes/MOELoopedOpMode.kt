package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBot
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBotConstantsImpl
import org.firstinspires.ftc.teamcode.constants.OpModeInterface
import org.firstinspires.ftc.teamcode.constants.Ref
import org.firstinspires.ftc.teamcode.constants.Ref.setRobotRef

abstract class MOELoopedOpMode : OpMode(), OpModeInterface, MOEBotConstantsImpl {
    lateinit var robot: MOEBot
    private var opModeIsActive: Boolean = false

    @Volatile
    private var isStopRequested = false

    override fun iRequestOpModeStop() = requestOpModeStop()

    override fun isActive(): Boolean = opModeIsActive && !iIsStopRequested

    override val iIsStopRequested: Boolean
        get() = isStopRequested

    override fun init() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE)
        setRefs()
        initRobot()
        moeInternalInit()
        setRobotRef(robot)
        initOpMode()

    }

    private fun initRobot() {
        robot = createRobot()
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

    open fun initLoop(): Boolean {
        return true
    }

    private fun postInitLoop() {
        notifyInitFinished()
    }

    var firstTime = true
    var currTime = 0L
    override fun loop() {
        if (firstTime) {
            if (!initLoop()) {
                telemetry.addLine("Init is not complete, please wait")
                return
            }
            offsetRobotValues()
            firstTime = false
            opModeIsActive=true
        }
        currTime = System.nanoTime()
        internalLoop()
        telemetry.addData("Loop time", (System.nanoTime() - currTime) / 1000000)

    }

    open fun internalLoop() {

    }


    private fun setRefs() {
        Ref.setRefs(this)
    }


    private fun notifyInitFinished() {
        telemetry.addLine("waiting for start".toString())
        telemetry.update()
    }

    private fun offsetRobotValues() {
        robot.offsetValues(this)
    }

    open fun moeInternalInit() {}

    open fun initOpMode() {}

    override fun stop() {
        isStopRequested = true
        opModeIsActive = false
        if (::robot.isInitialized) robot.stop()
    }
}
