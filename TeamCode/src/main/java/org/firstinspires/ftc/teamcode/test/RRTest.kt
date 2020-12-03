package org.firstinspires.ftc.teamcode.test

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.test.rr.drive.ThreeWheelOdo


class RRTest : OpMode() {
    lateinit var motors: List<DcMotorEx>
    lateinit var frontLeft: DcMotorEx
    lateinit var frontRight: DcMotorEx
    lateinit var backLeft: DcMotorEx
    lateinit var backRight: DcMotorEx
    lateinit var localizer: ThreeWheelOdo
    lateinit var gyro: BNO055IMU
    lateinit var dashboard: FtcDashboard

    override fun init() {
        dashboard = FtcDashboard.getInstance()

        val voltage = hardwareMap.voltageSensor.first().voltage
        frontLeft = hardwareMap.dcMotor["FLDM10"] as DcMotorEx
        frontLeft.direction = DcMotorSimple.Direction.REVERSE
        backLeft = hardwareMap.dcMotor["BLDM11"] as DcMotorEx
        backLeft.direction = DcMotorSimple.Direction.REVERSE
        frontRight = hardwareMap.dcMotor["FRDM12"] as DcMotorEx
        backRight = hardwareMap.dcMotor["BRDM13"] as DcMotorEx
        val packet = TelemetryPacket()
        packet.put("voltage is", voltage)
        dashboard.sendTelemetryPacket(packet);
        motors = listOf(
                frontLeft,
                frontRight,
                backLeft,
                backRight
        )
        motors.forEach {
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
//            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }


        val parameters = BNO055IMU.Parameters()
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        parameters.calibrationDataFile = "BNO055IMUCalibration.json" // see the calibration sample opmode

        parameters.loggingEnabled = true
        parameters.loggingTag = "IMU"
        parameters.accelerationIntegrationAlgorithm = JustLoggingAccelerationIntegrator()

        gyro = hardwareMap.get(BNO055IMU::class.java, "imu") as BNO055IMU
        gyro.initialize(parameters)


        localizer = ThreeWheelOdo(hardwareMap)

    }

    override fun loop() {

//        if (localizer.poseEstimate.x > 22) {
//            motors.forEach { it.power = 0.0 }
//        } else {
//            motors.forEach { it.power = 0.3 }
//        }
        localizer.update()
        val packet = TelemetryPacket()
        packet.put("rr_x", localizer.poseEstimate.x)
        packet.put("rr_y", localizer.poseEstimate.y)
        packet.put("rr_theta", Math.toDegrees(localizer.poseEstimate.heading))
        packet.put("raw_left", backRight.currentPosition)
        packet.put("raw_right", frontRight.currentPosition)
        packet.put("raw_strafe", backLeft.currentPosition)
        packet.put("raw_gyro", gyro.angularOrientation.firstAngle)
        dashboard.sendTelemetryPacket(packet)
    }


}

const val RIGHT_SCALAR = 306.3309693
const val LEFT_SCALAR = -307.3191489
const val STRAFE_SCALAR = -305.1867612

