package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig

import org.firstinspires.ftc.teamcode.autonomous.constants.AutonConstants
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Point

data class AutonPositionConfig(val positionOffsets: Point,
                               val afterSkystonePosition: Point,
                               val dumpSkystonePosition: Point,
                               val parkPosition: Point) {
    private fun flipAboutFieldYAxis(p: Point): Point = Point(AutonConstants.FIELD_SIZE - p.x, p.y)

    fun reflect(): AutonPositionConfig {

        return AutonPositionConfig(
                positionOffsets = flipAboutFieldYAxis(positionOffsets),
                afterSkystonePosition = flipAboutFieldYAxis(afterSkystonePosition),
                dumpSkystonePosition = flipAboutFieldYAxis(dumpSkystonePosition),
                parkPosition = flipAboutFieldYAxis(parkPosition)
        )
    }
}
