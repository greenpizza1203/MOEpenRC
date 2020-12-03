package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE
import org.firstinspires.ftc.teamcode.test.rr.drive.ThreeWheelOdo
import org.firstinspires.ftc.teamcode.utilities.external.toFixed


@TeleOp
class CorrectedEncoderDriveTest : OpMode() {
    lateinit var motors: List<DcMotorEx>
    lateinit var frontLeft: DcMotorEx
    lateinit var backLeft: DcMotorEx
    lateinit var frontRight: DcMotorEx
    lateinit var backRight: DcMotorEx
    lateinit var localizer: ThreeWheelOdo
    override fun init() {
//        multi = MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().telemetry)
        val voltage = hardwareMap.voltageSensor.first().voltage
        frontLeft = hardwareMap.dcMotor["FLM20"] as DcMotorEx
        frontLeft.direction = REVERSE
        backLeft = hardwareMap.dcMotor["BLM21"] as DcMotorEx
        backLeft.direction = REVERSE
        backRight = hardwareMap.dcMotor["FRM22"] as DcMotorEx
        frontRight = hardwareMap.dcMotor["BRM23"] as DcMotorEx

        motors = listOf(
                frontLeft,
                this.frontRight,
                backLeft,
                this.frontRight
        )
        motors.forEach {
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        }
        localizer = ThreeWheelOdo(hardwareMap)
        telemetry.addData("Init", "Complete")
        telemetry.update()
    }


    override fun loop() {
        localizer.update()
        telemetry.addData("wheels", localizer.getWheelPositions().map { it.toFixed() })
        telemetry.addData("wheels", localizer.poseEstimate)
        telemetry.update()
    }
}