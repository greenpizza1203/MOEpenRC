package org.firstinspires.ftc.teamcode.autonomous.constants

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.AutonPositionConfig
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Point

object AutonConstants {
    object Locations {
        val Left = AutonPositionConfig(
                positionOffsets = Point(0.0, 0.0), // Point(15.0, 96.0),
                afterSkystonePosition = Point(48.0, 96.0),
                dumpSkystonePosition = Point(48.0, 192.0),
                parkPosition = Point(63.0, 144.0)
        )
        val Right = Left.reflect()
    }


    const val FIELD_SIZE = 288.0
    const val OPEN_FOR_CAMERA_SERVO = 0.335
    const val TESTING = "fast"
}