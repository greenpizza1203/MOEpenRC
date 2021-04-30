//package org.firstinspires.ftc.teamcode.test
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp
//import com.qualcomm.robotcore.hardware.DcMotorEx
//import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
//import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOETeleOp
//import org.firstinspires.ftc.teamcode.test.rr.drive.FastThreeWheelOdo
//import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toDegrees
//import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
//import org.firstinspires.ftc.teamcode.utilities.external.toFixed
//import kotlin.math.PI
//import kotlin.math.abs
//
//
//@TeleOp
//@Disabled
//class TurnPIDTest : MOETeleOp() {
//    lateinit var motors: List<DcMotorEx>
//    lateinit var frontLeft: DcMotorEx
//    lateinit var backLeft: DcMotorEx
//    lateinit var frontRight: DcMotorEx
//    lateinit var backRight: DcMotorEx
//    lateinit var localizer: FastThreeWheelOdo
//    val turnPid = MOETurnPid(1.0, 0.0, 0.0)
//
//    init {
//        turnPid.setOutputLimits(0.4)
//
//    }
//
//    override fun initOpMode() {
//        localizer = FastThreeWheelOdo(hardwareMap)
//    }
//
//    override fun mainLoop() {
//        val left = 13174
//        val right = 15401
//        localizer.update()
//        val pose = localizer.poseEstimate
////        val heading = pose.heading
//        val scalar = ((left + right) / (2 * PI))
//        val heading = ((localizer.rightEncoder.motor.currentPosition + localizer.leftEncoder.motor.currentPosition) / scalar)
//        val rotations = 3
//        val lastStretch = heading < (rotations - 1) * 360.toRadians() + 240.toRadians()
//        val target = if (lastStretch) heading + 120.toRadians() else (rotations * 360).toRadians()
//        val actualHeading = if (lastStretch) (rotations - 1) * 360.toRadians() + heading else heading
//        telemetry.addData("raw", localizer.getRawPositions())
//        telemetry.addData("wheels", localizer.getWheelPositions().map { it.toFixed() })
//        telemetry.addData("pose", pose)
//        telemetry.addData("heading", heading.toDegrees())
//        telemetry.addData("target", target.toDegrees())
//
//        val rot = turnPid.getOutput(actualHeading, target)
//
//        telemetry.addData("ROT", rot)
//        if (gpad1.b()) {
//            robot.chassis.stop()
//            return
//        }
//
//        if (abs(rot) > 0.11) robot.chassis.turnPower(rot) else robot.chassis.stop()
//
//    }
//}