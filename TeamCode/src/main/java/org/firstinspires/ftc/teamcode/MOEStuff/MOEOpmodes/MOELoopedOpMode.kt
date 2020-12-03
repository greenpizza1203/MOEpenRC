package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes

//import androidx.annotation.CallSuper
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
//    @CallSuper
    override fun init() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE)
        opModeIsActive = false
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
            while (opModeIsActive && !initLoop()) {
                telemetry.addLine("Init is not complete, please wait")
            }
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
        if (::robot.isInitialized) robot.stop()
    }
}
