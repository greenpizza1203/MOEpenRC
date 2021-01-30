package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.test.rr.drive.FastThreeWheelOdo
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toDegrees
import org.firstinspires.ftc.teamcode.utilities.external.toFixed
import kotlin.math.abs


@TeleOp
class StrafeFixTest : MOETeleOp() {
    lateinit var motors: List<DcMotorEx>
    lateinit var frontLeft: DcMotorEx
    lateinit var backLeft: DcMotorEx
    lateinit var frontRight: DcMotorEx
    lateinit var backRight: DcMotorEx
    lateinit var localizer: FastThreeWheelOdo
    private val turnPid = MOETurnPid(1.0, 0.0, 0.0)

    init {
        turnPid.setOutputLimits(1.0)
    }

    override fun initOpMode() {
//        localizer = LeftWheelOdo(hardwareMap, (robot.gyro as MOEIMUGyro).imu)
        localizer = FastThreeWheelOdo(hardwareMap)
    }

    override fun mainLoop() {

        localizer.update()
        val pose = localizer.poseEstimate
        val heading = pose.heading
//        val heading = robot.gyro.angle-90.toRadians()
        telemetry.addData("raw", localizer.getRawPositions())
        telemetry.addData("wheels", localizer.getWheelPositions().map { it.toFixed() })
        telemetry.addData("pose", pose)
        telemetry.addData("heading", heading.toDegrees())
        telemetry.addData("imu", robot.gyro.angle.toDegrees())

        val rot = -turnPid.getOutput(heading, 0.0)
        telemetry.addData("ROT", rot)

        if (abs(gpad1.left.stick.x()) < 0.1) {
            robot.chassis.stop()
        } else {
            robot.chassis.setFromMecanum(0.0, gpad1.left.stick.x(), rot)
        }
//        if (abs(rot) > 0.11) robot.chassis.turnPower(rot) else robot.chassis.stop()

    }
}