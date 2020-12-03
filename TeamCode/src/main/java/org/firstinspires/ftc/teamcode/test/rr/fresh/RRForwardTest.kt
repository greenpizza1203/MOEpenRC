package org.firstinspires.ftc.teamcode.test.rr.fresh

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOEPidOptions
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOETurnPid
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.concurrent.thread
import kotlin.math.PI

@TeleOp
class RRForwardTest : OpMode() {
    val turnPid = MOETurnPid(3.8, 0.0, 29.0)
    lateinit var drive: SampleMecanumDrive

    lateinit var dashboard: FtcDashboard

//    var packet = DatagramPacket(buf, buf.size, address, 42069)
//    socket.send(packet)

    override fun init() {

        dashboard = FtcDashboard.getInstance()

        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE)

        drive = SampleMecanumDrive(hardwareMap)

        turnPid.input = {
            drive.poseEstimate.heading
        }
        turnPid.output = {
        }
        turnPid.setpoint = { PI * 0.5 }
        turnPid.setOutputLimits(1.0)

    }

    override fun loop() {


        val output = turnPid.getOutput()

//        drive.setMotorPowers(-output, -output, output, output)
        drive.setDrivePower(Pose2d(heading = output))
        drive.updatePoseEstimate()
        if (drive.poseEstimate.heading !in 0.85 * PI..PI) return
        val packet = TelemetryPacket()

//        packet.put("min", PI * 0.8)
        packet.put("goal", PI * 0.9)
//        packet.put("input", heading)
        packet.put("power", output)
//        packet.put("max", PI)
//        val heading = drive.poseEstimate.heading


        dashboard.sendTelemetryPacket(packet)


    }
}