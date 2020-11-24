package org.firstinspires.ftc.teamcode.test

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER
import com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE
import com.qualcomm.robotcore.util.ElapsedTime

@Disabled
@TeleOp
class EncoderDriveTest : OpMode() {
    lateinit var motors: List<DcMotorEx>
    lateinit var frontLeft: DcMotorEx
    lateinit var backLeft: DcMotorEx
    lateinit var frontRight: DcMotorEx
    lateinit var backRight: DcMotorEx
    lateinit var multi: MultipleTelemetry
    var dataHolder = IntArray(100000)
    override fun init() {
        multi = MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().telemetry)
        val voltage = hardwareMap.voltageSensor.first().voltage
        frontLeft = hardwareMap.dcMotor["FLDM10"] as DcMotorEx
        frontLeft.direction = REVERSE
        backLeft = hardwareMap.dcMotor["BLDM11"] as DcMotorEx
        backLeft.direction = REVERSE
        backRight = hardwareMap.dcMotor["BRDM12"] as DcMotorEx
        frontRight = hardwareMap.dcMotor["FRDM13"] as DcMotorEx

        multi.addData("voltage", voltage)
        multi.update()
        motors = listOf(
                frontLeft,
                this.frontRight,
                backLeft,
                this.frontRight
        )
        motors.forEach {
            it.mode = STOP_AND_RESET_ENCODER
            it.mode = RUN_WITHOUT_ENCODER
        }

    }

    private val timer = ElapsedTime()
    override fun init_loop() {
        timer.reset()
    }


    override fun loop() {
        motors.forEach { it.power = 1.0 }
        multi.addData("strafe", backLeft.currentPosition)
//        multi.addData("right", frontLeft.currentPosition)
//        multi.addData("left", this.frontRight.currentPosition)
//        multi.update()


    }
}