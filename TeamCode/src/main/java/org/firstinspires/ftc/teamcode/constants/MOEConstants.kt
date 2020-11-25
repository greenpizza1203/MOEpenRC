package org.firstinspires.ftc.teamcode.constants

import com.qualcomm.hardware.bosch.BNO055IMU

//import org.firstinspires.ftc.teamcode.utilities.external.PurePursuit.MOEPurePursuitOptions

//import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEdometry.OdometryWheelConfig as OWC

object MOEConstants {


//    object PurePursuit {
//        val DefaultOptions = MOEPurePursuitOptions(
//                overallMaxVelocity = 1.0,
//                spacing = 3.0,
//                tolerance = 0.0005,
//                smoothingA = 1 - 0.7,
//                smoothingB = 0.7,
//                turningConstant = 0.1,
//                lookBack = 4,
//                lookForward = 4,
//                lookAheadDistance = 20.0,
//                track_width = 28.0,
//                K_V = 0.0,
//                K_A = 0.0,
//                K_P = 0.0
//        )
//
//        const val FINISHED_TOLERANCE: Double = 0.5
//    }


    object Units {
        const val METER_PER_FEET = 0.3048
        const val FEET_PER_TILE = 2

        const val METERS_PER_TILE = FEET_PER_TILE * METER_PER_FEET
        const val ASTARS_PER_TILE = 48

        //        const val METERS_PER_ASTAR = 0.0127
        const val METERS_PER_ASTAR = METERS_PER_TILE / ASTARS_PER_TILE
        const val ASTARS_PER_METER = ASTARS_PER_TILE / METERS_PER_TILE

        const val TICS_PER_INCH = 1170.0 //tics per inch of encoder
//        const val TICS_PER_ASTAR = TICS_PER_INCH / 2
    }


    object Gamepad {
        const val ON_THRESHOLD = 0.5
    }

    object GyroConfig {
        val parameters = BNO055IMU.Parameters().apply {
            angleUnit = BNO055IMU.AngleUnit.RADIANS
            mode = BNO055IMU.SensorMode.IMU
            accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        }
    }
}
