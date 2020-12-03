package org.firstinspires.ftc.teamcode.test.rr.fresh

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOERawPid
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.test.rr.drive.ThreeWheelOdo
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toDegrees

@TeleOp
class RRTurnTest : OpMode() {
    val forward = MOERawPid(0.08, 0.0, 0.36)
    val strafe = MOERawPid(0.08, 0.0, 0.36)

    lateinit var drive: SampleMecanumDrive

    lateinit var dashboard: FtcDashboard

    //    var packet = DatagramPacket(buf, buf.size, address, 42069)
//    socket.send(packet)
    lateinit var imu: BNO055IMU

    override fun init() {

        dashboard = FtcDashboard.getInstance()

        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE)

        forward.input = {
            val d = ((drive.localizer as ThreeWheelOdo).leftEncoder.currentPosition + (drive.localizer as ThreeWheelOdo).rightEncoder.currentPosition) / 305.0
            d
        }
        forward.setpoint = { 0.0 }
        forward.setOutputLimits(1.0)
        strafe.input = {
            (drive.localizer as ThreeWheelOdo).strafeEncoder.currentPosition / 305.0
        }
        strafe.setpoint = { 0.0 }
        strafe.setOutputLimits(1.0)

        drive = SampleMecanumDrive(hardwareMap)
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

//        turnPid.input = {
//            drive.poseEstimate.heading
//        }
//        turnPid.output = {
//        }
//        turnPid.setpoint = { PI * 0.5 }
//        turnPid.setOutputLimits(1.0)

    }

    override fun loop() {


        val forwardPower = forward.getOutput()
        val strafePower = strafe.getOutput()

//        drive.setMotorPowers(-output, -output, output, output)
        drive.setDrivePower(Pose2d(x = -forwardPower, y = strafePower, heading = 1.0))
        drive.updatePoseEstimate()
//        if (drive.poseEstimate.heading !in 0.85 * PI..PI) return
        val packet = TelemetryPacket()
        val localizer = drive.localizer as ThreeWheelOdo
//        packet.put("left", localizer.leftEncoder.currentPosition)
//        packet.put("right", localizer.rightEncoder.currentPosition)
        packet.put("vertical", localizer.leftEncoder.currentPosition + localizer.rightEncoder.currentPosition)
        packet.put("strafe", localizer.strafeEncoder.currentPosition)
        packet.put("gyro", imu.angularOrientation.firstAngle.toDouble().toDegrees())

//        packet.put("min", PI * 0.8)
//        packet.put("goal", PI * 0.9)
//        packet.put("input", heading)
//        packet.put("power", output)
//        packet.put("max", PI)
//        val heading = drive.poseEstimate.heading


        dashboard.sendTelemetryPacket(packet)


    }
}