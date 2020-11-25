package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBot
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBotConstantsImpl
import org.firstinspires.ftc.teamcode.constants.OpModeInterface
import org.firstinspires.ftc.teamcode.constants.Ref
import org.firstinspires.ftc.teamcode.utilities.internal.addData

abstract class MOEAuton : LinearOpMode(), OpModeInterface, MOEBotConstantsImpl {
    lateinit var robot: MOEBot
    override fun isActive(): Boolean = opModeIsActive()
    override fun iRequestOpModeStop() = requestOpModeStop()


    override val iIsStopRequested: Boolean
        get() = this.isStopRequested


    final override fun runOpMode() {
        Ref.setRefs(this)
        robot = createRobot()
        robot.gyro.init()
        Ref.setRobotRef(robot)
        initOpMode()
        moeInternalPostInit()
        waitForStart()
//        globalTimer.reset()
        offsetRobotValues()
        run()
        postRun()
    }


    open fun postRun() {

    }

    private fun initRobot() {
        robot = createRobot()
    }

    fun waitForStop() {
        while (!isStopRequested) {
            notifyTelemetry("waiting for stop")
        }
    }

    override fun waitForStart() {
        while (!isStarted && !isStopRequested) {
            notifyTelemetry("waiting for start")
        }
    }

    private fun moeDoubleInternalInit() {
        Ref.setRefs(this)
    }


    private fun notifyTelemetry(message: String) {
        telemetry.addData(message)
        telemetry.update()
    }


    open fun initOpMode() {

    }

    open fun moeInternalPostInit() {

    }


    private fun offsetRobotValues() {
        robot.offsetValues(this)
    }

    abstract fun run()
}