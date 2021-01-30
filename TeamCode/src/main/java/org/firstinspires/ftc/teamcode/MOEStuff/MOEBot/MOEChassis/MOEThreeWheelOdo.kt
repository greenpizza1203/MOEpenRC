package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEHardware.MOEEncoder
import org.firstinspires.ftc.teamcode.constants.MOEHardwareConstants
import org.firstinspires.ftc.teamcode.constants.Ref.hardwareMap
import org.firstinspires.ftc.teamcode.test.rr.util.Encoder
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

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
class MOEThreeWheelOdo : ThreeTrackingWheelLocalizer(listOf(
        Pose2d(-0.343, 6.84484540983),  // left parallel
        Pose2d(-0.343, -8.027830534778314),  // right parallel
        Pose2d(-0.276, 0.343, 90.toRadians()) //strafe
)) {

    private val leftEncoder = MOEEncoder(MOEHardwareConstants.DriveTrain.Motors.Configs.BackRight)
    private val rightEncoder = MOEEncoder(MOEHardwareConstants.DriveTrain.Motors.Configs.FrontRight)
    private val strafeEncoder = MOEEncoder(MOEHardwareConstants.DriveTrain.Motors.Configs.FrontLeft)
    private var LEFT_SCALAR = -305.3191489
    private var RIGHT_SCALAR = 305.3309693
    private var STRAFE_SCALAR = 305.1867612

    override fun getWheelPositions(): List<Double> {

        return listOf(
                leftEncoder.position / LEFT_SCALAR,
                rightEncoder.position / RIGHT_SCALAR,
                strafeEncoder.position / STRAFE_SCALAR
        )

    }

    override fun getWheelVelocities(): List<Double> {
        return listOf(
                leftEncoder.velocity / LEFT_SCALAR,
                rightEncoder.velocity / RIGHT_SCALAR,
                strafeEncoder.velocity / STRAFE_SCALAR
        )
    }


}