package org.firstinspires.ftc.teamcode.test.rr.drive.opmode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive
import kotlin.math.abs

/**
 * This is a simple teleop routine for testing localization. Drive the robot around like a normal
 * teleop routine and make sure the robot's estimated pose matches the robot's actual pose (slight
 * errors are not out of the ordinary, especially with sudden drive motions). The goal of this
 * exercise is to ascertain whether the localizer has been configured properly (note: the pure
 * encoder localizer heading may be significantly off if the track width has not been tuned).
 */
@Config
@TeleOp(group = "drive")
class LocalizationTest : LinearOpMode() {

    @Throws(InterruptedException::class)
    override fun runOpMode() {
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        val drive = SampleMecanumDrive(hardwareMap)
        waitForStart()
        while (!isStopRequested) {
            val baseVel = Pose2d(
                    (-gamepad1.left_stick_y).toDouble(),
                    (-gamepad1.left_stick_x).toDouble(),
                    (-gamepad1.right_stick_x).toDouble()
            )
            var vel: Pose2d
            vel = if (abs(baseVel.x) + abs(baseVel.y) + abs(baseVel.heading) > 1) {
                // re-normalize the powers according to the weights
                val denom = abs(baseVel.x) + abs(baseVel.y) + abs(baseVel.heading)
                Pose2d(
                        baseVel.x,
                        baseVel.y,
                        baseVel.heading
                ).div(denom)
            } else {
                baseVel
            }
            drive.setDrivePower(vel)
            drive.updatePoseEstimate()
            System.currentTimeMillis()
//            telemetry.addData(drive.poseEstimate.)
//            telemetry.update()
            val poseEstimate = drive.poseEstimate
            telemetry.addData("y", poseEstimate.y)
            telemetry.addData("heading", poseEstimate.heading)
            telemetry.addData("wheels", (drive.localizer as ThreeTrackingWheelLocalizer).getWheelPositions())

            telemetry.update()
        }
    }

    companion object {
        var VY_WEIGHT = 1.0
        var OMEGA_WEIGHT = 1.0
    }
}


fun main() {
//    val socket = DatagramSocket(5000)
//    val buf = ByteArray(72)
//    val packet = DatagramPacket(buf, buf.size)
////    socket.bind(InetSocketAddress(5000))
//    while (true) {
////        println("waiting")
//
//        socket.receive(packet)
////        println("got it")
//        val wrap = ByteBuffer.wrap(buf)
//        wrap.order(ByteOrder.LITTLE_ENDIAN)
////        println(wrap.getDouble(0))
//    }

//    socket.close()


}