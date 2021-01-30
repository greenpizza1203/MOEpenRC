package org.firstinspires.ftc.teamcode.test

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.teleop.UltimateGoalTeleOp
import org.firstinspires.ftc.teamcode.test.rr.drive.LeftWheelOdo
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.MOEThreeWheelOdo
import org.firstinspires.ftc.teamcode.test.rr.util.Encoder


@TeleOp
class AllLocalizationTest : UltimateGoalTeleOp() {
    lateinit var twoWheel: LeftWheelOdo
    lateinit var threeWheel: MOEThreeWheelOdo
    private lateinit var leftEncoder: Encoder
    private lateinit var rightEncoder: Encoder
    private lateinit var strafeEncoder: Encoder
    override fun initOpMode() {
        super.initOpMode()
        leftEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "BRM23"))
        rightEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "FRM22"))
        strafeEncoder = Encoder(hardwareMap.get(DcMotorEx::class.java, "FLM20"))
        listOf(leftEncoder, rightEncoder, strafeEncoder).map {
            val mode = it.motor.mode
            it.motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.motor.mode = mode
        }
        val imu = hardwareMap.get(BNO055IMU::class.java, "imu")
        imu.initialize(object : BNO055IMU.Parameters() {

        })
        twoWheel = LeftWheelOdo(leftEncoder, strafeEncoder, imu)
        threeWheel = MOEThreeWheelOdo()
    }

    override fun mainLoop() {
//        super.mainLoop()
        twoWheel.update()
        threeWheel.update()
        telemetry.addData("Two wheel", twoWheel.poseEstimate)
        telemetry.addData("Three wheel", threeWheel.poseEstimate)
//        twoWheel.poseEstimate
    }

}