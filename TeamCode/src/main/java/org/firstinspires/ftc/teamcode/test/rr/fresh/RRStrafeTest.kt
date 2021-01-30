package org.firstinspires.ftc.teamcode.test.rr.fresh

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOERawPid
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.test.rr.drive.FastThreeWheelOdo
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.closestAngleDifference


@TeleOp
class RRStrafeTest : OpMode() {
    val forward = MOERawPid(0.08, 0.0, 0.36)
    val turnPid = MOETurnPid(3.2, 0.0, 0.0)
//    val forward = MOERawPid(0.0, 0.0, 0.0)
//    val turnPid = MOETurnPid(0.0, 0.0, 0.0)

    lateinit var drive: SampleMecanumDrive

    lateinit var dashboard: FtcDashboard
    lateinit var imu: BNO055IMU
    override fun init() {
        dashboard = FtcDashboard.getInstance()
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE)
        drive = SampleMecanumDrive(hardwareMap)
        forward.input = {
            val d = ((drive.localizer as FastThreeWheelOdo).leftEncoder.currentPosition + (drive.localizer as FastThreeWheelOdo).rightEncoder.currentPosition) / 305.0
            d
        }
        forward.setpoint = { 0.0 }
        forward.setOutputLimits(1.0)

        gamepad1.setJoystickDeadzone(0.08F)
        val parameters = BNO055IMU.Parameters()

        parameters.mode = BNO055IMU.SensorMode.IMU
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        parameters.loggingEnabled = false

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU::class.java, "imu")

        imu.initialize(parameters)
        turnPid.input = {
//            imu.angularOrientation.firstAngle.toDouble()
//            val d = (((drive.localizer as StandardTrackingWheelLocalizer).leftEncoder.currentPosition - (drive.localizer as StandardTrackingWheelLocalizer).rightEncoder.currentPosition) / 305.0) / 98.4575137635

//            d
            imu.angularOrientation.firstAngle.toDouble()
        }

        turnPid.setpoint = { 0.0 }
        turnPid.setOutputLimits(1.0)
    }


    override fun loop() {
        val forward = forward.getOutput()
        val heading = turnPid.getOutput()
        val packet = TelemetryPacket()

        packet.put("y", drive.poseEstimate.y)
        packet.put("heading", imu.angularOrientation.firstAngle.toDouble().closestAngleDifference(0.0))
        packet.put("heading", drive.poseEstimate.heading)
        packet.put("forwardFix", forward)
        packet.put("left", (drive.localizer as FastThreeWheelOdo).leftEncoder.currentPosition)
        packet.put("right", (drive.localizer as FastThreeWheelOdo).rightEncoder.currentPosition)


        dashboard.sendTelemetryPacket(packet)


        if (gamepad1.left_trigger > 0.2) {
            drive.setDrivePower(Pose2d())
        } else {
//            drive.setDrivePower(Pose2d(x = forward, y = 0.2, heading = heading))
            drive.setDrivePower(Pose2d(x = -forward, y = 0.2, heading = heading))

        }
        drive.updatePoseEstimate()


    }

    @Volatile
    var running = true
    override fun stop() {
        running = false
    }

//    companion object {
//
//        init {
//            val socket = DatagramSocket(5000)
//
//            thread {
//                val buf = ByteArray(72)
//                val packet = DatagramPacket(buf, buf.size)
//                while (true) {
//                    socket.receive(packet)
//                    val wrap = ByteBuffer.wrap(buf)
//                    wrap.order(ByteOrder.LITTLE_ENDIAN)
//                    strafe.setOptions(MOEPidOptions(P = wrap.double, I = wrap.double, D = wrap.double))
//                }
////                Log.e("shutting down server", "bye")
////                socket.close()
//
//            }
//        }
//    }
}