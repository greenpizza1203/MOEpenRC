package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEdometry

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBotConstantsImpl
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.Odometry.LeftForward
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.Odometry.RightForward
import org.firstinspires.ftc.teamcode.constants.OldMOEHardwareConstants.Odometry.Strafe
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.firstinspires.ftc.teamcode.constants.Ref.robot
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.MOEtion
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Point
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toDegrees
import kotlin.math.cos
import kotlin.math.sin


class MOEdometrySystem(val config: MOEBotConstantsImpl) : MOELocalizationSystem {

    override fun moetion(): MOEtion {
        return MOEtion(fieldCentricPose, robot.gyro.angle)
    }

    override fun getRawTheta(): Double {
        return angleRad.toDegrees()
    }

    val rightForwardWheel = MOEDometryWheel(RightForward)
    val leftForwardWheel = MOEDometryWheel(LeftForward)
    val strafeWheel = MOEDometryWheel(Strafe)
    var oldForwardDist = 0.0
    var oldStrafeDist = 0.0
    var angleRad = 0.0

    fun resetValues() {
        rightForwardWheel.resetValues()
        strafeWheel.resetValues()
        leftForwardWheel.resetValues()
    }

    var debugPose = Point(0.0, 0.0)
    var fieldCentricPose = (config.getRobotInitialState().robotInitial.pose.clone()) / 2.0

    private fun runLoop() {
//        val currTime = System.currentTimeMillis()
        val rightForwardDist = rightForwardWheel.getValue()
        val averageForwardDist = (rightForwardDist + leftForwardWheel.getValue()) / 2.0
        val forwardDelta = averageForwardDist - oldForwardDist
        oldForwardDist = averageForwardDist

        angleRad = (rightForwardDist - averageForwardDist) / MOEdometryConstants.TICS_PER_RADIANS

        val strafeDist = strafeWheel.getValue()
        val strafeCorrected = strafeDist - (angleRad * MOEdometryConstants.TICS_PER_STRAFE_ROTATION)
        val strafeDelta = strafeCorrected - oldStrafeDist
        oldStrafeDist = strafeCorrected


        debugPose = Point(averageForwardDist, strafeDist)

        //update pose
        fieldCentricPose.x += forwardDelta * sin(angleRad) + strafeDelta * cos(angleRad)
        fieldCentricPose.y += (forwardDelta * cos(angleRad) - strafeDelta * sin(angleRad))
//        telemetry.addData("odoTime", System.currentTimeMillis() - currTime)
    }

    lateinit var launch: Job
    fun launchLoop() {
        launch = GlobalScope.launch {
            //todo: will fail if app crashes
            while (isActive && !moeOpMode.iIsStopRequested) {
                runLoop()
            }
        }
    }

    fun stop() {
        launch.cancel()
    }
}
