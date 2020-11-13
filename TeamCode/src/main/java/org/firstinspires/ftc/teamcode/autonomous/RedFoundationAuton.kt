package org.firstinspires.ftc.teamcode.autonomous

//import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.opmodeutils.MOEGamePad.Button
import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton
import org.firstinspires.ftc.teamcode.utilities.internal.addData
import org.firstinspires.ftc.teamcode.utilities.internal.wait

@Disabled
@Autonomous
class RedFoundationAuton : MOEAuton() {
    override fun initOpMode() {
        Log.e("stuffe", "stuffe")
        telemetry.addData("testagain")
    }

    override fun run() {
        robot.chassis.encoders.moveRightInches(12.0)
        robot.chassis.encoders.moveForwardInches(39.0, 0.3)

        robot.foundation.moveDown()
        wait(3000)

        robot.chassis.encoders.moveBackwardInches(28.0, 0.3)
        robot.chassis.turnTo(-90.0)
        wait(3000)

        robot.foundation.moveUp()
        wait(3000)

//        robot.chassis.astar.jitter(4.0, 0.4)
    }
}
