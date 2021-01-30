package org.firstinspires.ftc.teamcode.test.rr.drive

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.test.rr.util.Encoder
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
import java.util.*

/*
* Sample tracking wheel localizer implementation assuming the standard configuration:
*
*    /--------------\
*    |     ____     |
*    |     ----     |
*    | ||        || |
*    | ||        || |
*    |              |
*    |              |
*    \--------------/
*
*/
@Config
class FastThreeWheelOdo(hardwareMap: HardwareMap) : ThreeTrackingWheelLocalizer(listOf(
        Pose2d(-0.343, 6.84484540983),  // left parallel
        Pose2d(-0.343, -8.027830534778314),  // right parallel
        Pose2d(-0.276, 0.343, 90.toRadians()) //strafe
)) {
    val leftEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "BRM23"))
    val rightEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "FRM22"))
    val strafeEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "FLM20"))
    var LEFT_SCALAR = -305.3191489
    var RIGHT_SCALAR = 305.3309693
    var STRAFE_SCALAR = 305.1867612

    init {
        listOf(leftEncoder, rightEncoder, strafeEncoder).map {
            val mode = it.motor.mode
            it.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.motor.mode = mode
        }
    }

    fun getRawPositions(): List<Int> {

        return listOf(
                leftEncoder.motor.currentPosition,
                rightEncoder.motor.currentPosition,
                strafeEncoder.motor.currentPosition
        )

    }

    override fun getWheelPositions(): List<Double> {

        return listOf(
                leftEncoder.motor.currentPosition / LEFT_SCALAR,
                rightEncoder.motor.currentPosition / RIGHT_SCALAR,
                strafeEncoder.motor.currentPosition / STRAFE_SCALAR
        )

    }

    override fun getWheelVelocities(): List<Double> {
        return listOf(
                leftEncoder.motor.velocity / LEFT_SCALAR,
                rightEncoder.motor.velocity / RIGHT_SCALAR,
                strafeEncoder.motor.velocity / STRAFE_SCALAR
        )
    }

    companion object {
        var TICKS_PER_REV = 0.0
        var WHEEL_RADIUS = 2.0 // in
        var LATERAL_DISTANCE = 10.0 // in; distance between the left and right wheels
        var FORWARD_OFFSET = 4.0 // in; offset of the lateral wheel
        fun encoderTicksToInches(ticks: Double): Double {
            val wheelCircumference = WHEEL_RADIUS * 2 * Math.PI
            return wheelCircumference * ticks / TICKS_PER_REV
        }
    }


}