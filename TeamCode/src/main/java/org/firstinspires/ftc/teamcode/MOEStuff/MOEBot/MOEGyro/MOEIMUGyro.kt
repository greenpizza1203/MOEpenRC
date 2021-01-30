package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro

import com.qualcomm.hardware.bosch.BNO055IMU
import org.firstinspires.ftc.teamcode.constants.MOEConstants.GyroConfig
import org.firstinspires.ftc.teamcode.constants.Ref.hardwareMap
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.firstinspires.ftc.teamcode.constants.Ref.telemetry
import org.firstinspires.ftc.teamcode.utilities.internal.get

class MOEIMUGyro : MOEGyro() {
    val imu = hardwareMap.get(BNO055IMU::class, "imu")
    private var initStarted = false

    override fun getRawAngle() = imu.angularOrientation.firstAngle.toDouble()

    /**
     * Initializes IMU parameters-126, -91.7
     */
    override fun init(sync: Boolean) {
        if (!initStarted) {
            initStarted = true
            imu.initialize(GyroConfig.parameters)
        }
        if (sync) {
            waitForGyroInit()
        }
    }

    private fun waitForGyroInit() {
        while (!moeOpMode.iIsStopRequested && !initFinished()) {
//            telemetry.addData("Gyro Status", imu.calibrationStatus)
//            telemetry.update()
        }

    }

    override fun initFinished() = imu.isGyroCalibrated
}