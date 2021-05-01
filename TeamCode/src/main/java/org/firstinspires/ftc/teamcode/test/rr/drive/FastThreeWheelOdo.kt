package org.firstinspires.ftc.teamcode.test.rr.drive

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer
import com.qualcomm.robotcore.hardware.DcMotorEx
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
class FastThreeWheelOdo(hardwareMap: HardwareMap) : ThreeTrackingWheelLocalizer(Arrays.asList(
        Pose2d(0.0, LATERAL_DISTANCE / 2, 0.0),  // left
        Pose2d(0.0, -LATERAL_DISTANCE / 2, 0.0),  // right
        Pose2d(FORWARD_OFFSET, 0.0, Math.toRadians(90.0)) // front
)) {
    private val leftEncoder: Encoder
    private val rightEncoder: Encoder
    private val frontEncoder: Encoder

    override fun getWheelPositions(): List<Double> {
        return Arrays.asList(
                encoderTicksToInches(leftEncoder.getCurrentPosition().toDouble()) * X_MULTIPLIER,
                encoderTicksToInches(rightEncoder.getCurrentPosition().toDouble()) * X_MULTIPLIER,
                encoderTicksToInches(frontEncoder.getCurrentPosition().toDouble()) * Y_MULTIPLIER
        )
    }

    override fun getWheelVelocities(): List<Double>? {
        // TODO: If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
        //  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
        //  compensation method
        return Arrays.asList(
                encoderTicksToInches(leftEncoder.getCorrectedVelocity()) * X_MULTIPLIER,
                encoderTicksToInches(rightEncoder.getCorrectedVelocity()) * X_MULTIPLIER,
                encoderTicksToInches(frontEncoder.getCorrectedVelocity()) * Y_MULTIPLIER
        )
    }

    companion object {
        var TICKS_PER_REV = 8192.0
        var WHEEL_RADIUS = 0.7480 // in
        var GEAR_RATIO = 1.0 // output (wheel) speed / input (encoder) speed
        var LATERAL_DISTANCE = 15.00158 // in; distance between the left and right wheels
        var FORWARD_OFFSET = -0.2756 // in; offset of the lateral wheel
        var X_MULTIPLIER = 1.001785292
        var Y_MULTIPLIER = 1.002938262
        fun encoderTicksToInches(ticks: Double): Double {
            return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV
        }
    }

    init {
        leftEncoder = Encoder(hardwareMap[DcMotorEx::class.java, "BRM13"])
        rightEncoder = Encoder(hardwareMap[DcMotorEx::class.java, "BLM23"])
        frontEncoder = Encoder(hardwareMap[DcMotorEx::class.java, "BIM22"])

        // TODO: reverse any encoders using Encoder.setDirection(Encoder.Direction.REVERSE)
        leftEncoder.setDirection(Encoder.Direction.REVERSE)
        rightEncoder.setDirection(Encoder.Direction.REVERSE)
    }
}