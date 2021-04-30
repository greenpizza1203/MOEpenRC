//package org.firstinspires.ftc.teamcode.test.rr.drive
//
//import com.acmerobotics.dashboard.config.Config
//import com.acmerobotics.roadrunner.geometry.Pose2d
//import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer
//import com.qualcomm.hardware.bosch.BNO055IMU
//import com.qualcomm.robotcore.hardware.DcMotor
//import com.qualcomm.robotcore.hardware.DcMotorEx
//import com.qualcomm.robotcore.hardware.HardwareMap
//import org.firstinspires.ftc.teamcode.test.rr.util.Encoder
//import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
//
///*
//* Sample tracking wheel localizer implementation assuming the standard configuration:
//*
//*    /--------------\
//*    |     ____     |
//*    |     ----     |
//*    | ||        || |
//*    | ||        || |
//*    |              |
//*    |              |
//*    \--------------/
//*
//*/
//@Config
//class LeftWheelOdo(val leftEncoder: Encoder, val strafeEncoder: Encoder, val imu: BNO055IMU) : TwoTrackingWheelLocalizer(listOf(
//        Pose2d(-0.343, 6.84484540983),  // left parallel
////        Pose2d(-0.343, -8.027830534778314),  // right parallel
//        Pose2d(-0.276, 0.343, 90.toRadians()) //strafe
//)) {
//
//    var LEFT_SCALAR = -305.3191489
//
//    //    var RIGHT_SCALAR = 305.3309693
//    var STRAFE_SCALAR = 305.1867612
//
////    init {
////        listOf(leftEncoder, strafeEncoder).map {
////            val mode = it.motor.mode
////            it.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
////            it.motor.mode = mode
////        }
////    }
//
//
//    override fun getWheelPositions(): List<Double> {
//
//        return listOf(
//                leftEncoder.motor.currentPosition / LEFT_SCALAR,
////                rightEncoder.motor.currentPosition / RIGHT_SCALAR,
//                strafeEncoder.motor.currentPosition / STRAFE_SCALAR
//        )
//
//    }
//
//    override fun getWheelVelocities(): List<Double> {
//        return listOf(
//                leftEncoder.motor.velocity / LEFT_SCALAR,
////                rightEncoder.motor.velocity / RIGHT_SCALAR,
//                strafeEncoder.motor.velocity / STRAFE_SCALAR
//        )
//    }
//
//    override fun getHeading(): Double {
//        return imu.angularOrientation.firstAngle.toDouble()
//    }
//
//
//}