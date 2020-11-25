package org.firstinspires.ftc.teamcode.test.rr.drive

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.test.rr.util.Encoder
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
class StandardTrackingWheelLocalizer(hardwareMap: HardwareMap) : ThreeTrackingWheelLocalizer(Arrays.asList(
        Pose2d(-0.343, 7.835),  // left parallel
        Pose2d(-0.343, -7.835),  // right parallel
        Pose2d(-0.276, 0.343, Math.toRadians(90.0)) //strafe
)) {
     val leftEncoder: Encoder
     val rightEncoder: Encoder
     val frontEncoder: Encoder
    var RIGHT_SCALAR = -305.3309693
    var LEFT_SCALAR = -306.3191489
    var STRAFE_SCALAR = 305.1867612

    init {
        leftEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "FRDM13"))
        rightEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "FLDM10"))
        frontEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "BLDM11"))
        listOf(leftEncoder, rightEncoder, frontEncoder).map {
            val mode = it.motor.mode
            it.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.motor.mode = mode
        }
    }

    override fun getWheelPositions(): List<Double> {

        return listOf(
                leftEncoder.motor.currentPosition / LEFT_SCALAR,
                rightEncoder.motor.currentPosition / RIGHT_SCALAR,
                frontEncoder.motor.currentPosition / STRAFE_SCALAR
        )

    }

    override fun getWheelVelocities(): List<Double> {
        return Arrays.asList(
                leftEncoder.motor.velocity / LEFT_SCALAR,
                rightEncoder.motor.velocity / RIGHT_SCALAR,
                frontEncoder.motor.velocity / STRAFE_SCALAR
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