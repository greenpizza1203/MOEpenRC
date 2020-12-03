package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEIMUGyro
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
import org.firstinspires.ftc.teamcode.test.rr.drive.ThreeWheelOdo
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toDegrees
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
import org.firstinspires.ftc.teamcode.utilities.external.toFixed
import kotlin.math.PI
import kotlin.math.abs


@TeleOp
class ForwardFixTest : MOETeleOp() {
    lateinit var motors: List<DcMotorEx>
    lateinit var frontLeft: DcMotorEx
    lateinit var backLeft: DcMotorEx
    lateinit var frontRight: DcMotorEx
    lateinit var backRight: DcMotorEx
    lateinit var localizer: ThreeWheelOdo
    private val turnPid = MOETurnPid(1.0, 0.0, 0.0)

    init {
        turnPid.setOutputLimits(1.0)
    }

    override fun initOpMode() {
//        localizer = LeftWheelOdo(hardwareMap, (robot.gyro as MOEIMUGyro).imu)
        localizer = ThreeWheelOdo(hardwareMap)
    }

    override fun mainLoop() {

        localizer.update()
        val pose = localizer.poseEstimate
        telemetry.addData("raw", localizer.getRawPositions())
        telemetry.addData("wheels", localizer.getWheelPositions().map { it.toFixed() })
        telemetry.addData("pose", pose)
        val scalar = (28575 / (2 * PI))
//        val heading = ((localizer.rightEncoder.motor.currentPosition + localizer.leftEncoder.motor.currentPosition) / scalar)
        val heading = robot.gyro.angle - 90.toRadians()
//        val heading = pose.heading
        telemetry.addData("heading", heading.toDegrees())

        val rot = -turnPid.getOutput(heading, 0.0)
        telemetry.addData("ROT", rot)

        if (abs(gpad1.left.stick.y()) < 0.1) {
            robot.chassis.stop()
        } else {
            robot.chassis.setFromMecanum(gpad1.left.stick.y(), 0.0, rot)
        }
//        if (abs(rot) > 0.11) robot.chassis.turnPower(rot) else robot.chassis.stop()

    }
}