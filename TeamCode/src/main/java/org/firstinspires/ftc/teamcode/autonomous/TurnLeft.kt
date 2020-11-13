package org.firstinspires.ftc.teamcode.autonomous

//import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.opmodeutils.MOEGamePad.Button
import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.Powers
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton
import org.firstinspires.ftc.teamcode.utilities.internal.addData

@Autonomous
class TurnLeft : MOEAuton() {
    override fun initOpMode() {
        Log.e("stuffe", "stuffe")
        telemetry.addData("testagain")
    }

    private val timer = ElapsedTime()
    override fun run() {
        timer.reset()
        while (timer.seconds() < 20 && opModeIsActive()) {
            telemetry.addData("waiting", timer.milliseconds())
            telemetry.update()
        }
        robot.chassis.setPower(Powers.fromMechanum(0.0, -0.5, -0.5))
        timer.reset()
        while (timer.seconds() < 2 && opModeIsActive()) {
            telemetry.addData("waiting", timer.milliseconds())
            telemetry.update()
        }
        robot.chassis.stop()
    }
}
