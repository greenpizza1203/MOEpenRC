package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.cos
import kotlin.math.sin
@Disabled
@TeleOp(name = "TestTeleop", group = "Teleop")
class TestTeleop : OpMode() {
    lateinit var frontLeftMotor: DcMotor
    lateinit var frontRightMotor: DcMotor
    lateinit var backLeftMotor: DcMotor
    lateinit var backRightMotor: DcMotor
    lateinit var intakeMotor: DcMotor
    lateinit var outerShooterMotor: DcMotorEx
    lateinit var innerShooterMotor: DcMotorEx

    //    lateinit var trigger: CRServo
    lateinit var trigger: Servo

    //    lateinit var ma3: AnalogInput
//    var ma3_offset = 0.0
    var triggerTarget = 0.0

    //    var ma3Wrapped: WrapperHandler = WrapperHandler(5.0) {
//        ma3.voltage-ma3_offset
//    }
    lateinit var imu: BNO055IMU
    override fun init() {
        frontLeftMotor = hardwareMap.dcMotor["FLM20"]
        frontLeftMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        frontRightMotor = hardwareMap.dcMotor["FRM22"]
        frontRightMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        backLeftMotor = hardwareMap.dcMotor["BLM21"]
        backLeftMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        backRightMotor = hardwareMap.dcMotor["BRM23"]
        backRightMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        frontLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        backLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        intakeMotor = hardwareMap.dcMotor["INM13"]
        outerShooterMotor = hardwareMap.dcMotor["OFM10"] as DcMotorEx
        innerShooterMotor = hardwareMap.dcMotor["IFM11"] as DcMotorEx
//        ma3 = hardwareMap.analogInput["STE11"]
//        ma3_offset = ma3.voltage
        trigger = hardwareMap.servo["STS11"]
//      trigger = hardwareMap.crservo["STS11"]
//      trigger.power
        gamepad1.setJoystickDeadzone(0.1f)
        trigger.setPosition(0.2)


//        if(ma3Wrapped.getValue() >= 3.75 && ma3Wrapped.getValue() <= 5.0){
//            var triggerTarget = 2.5
//        }
//        else if(ma3Wrapped.getValue() >= 1.25 && ma3Wrapped.getValue() <= 3.75) {
//            var triggerTarget = 0.0
//        }
//        else if(ma3Wrapped.getValue() >= -1.25 && ma3Wrapped.getValue() <= 1.25) {
//            var triggerTarget = -2.5
//        }
//        else if(ma3Wrapped.getValue() >= -3.75 && ma3Wrapped.getValue() <= -1.25) {
//            var triggerTarget = -5.0
//        }
//        else if(ma3Wrapped.getValue() >= -5 && ma3Wrapped.getValue() <= -3.75) {
//            var triggerTarget = -7.5
//        }
//        else{
//            var triggerTarget = 0.0
//        }
        imu = hardwareMap.get(BNO055IMU::class.java, "imu")
        imu.initialize(BNO055IMU.Parameters())


    }

    val timer = ElapsedTime()

    override fun loop() {

        var rawY = gamepad1.left_stick_y.toDouble()
        var rawX = gamepad1.left_stick_x.toDouble()
        var rx = gamepad1.right_stick_x.toDouble()

        val angle = imu.angularOrientation.firstAngle
        val x = rawX * cos(angle) - rawY * sin(angle)
        val y = rawX * sin(angle) + rawY * cos(angle)
        frontLeftMotor.power = (-y + x + rx) * 0.6
        backLeftMotor.power = (-y - x + rx) * 0.6
        frontRightMotor.power = (-y - x - rx) * 0.6
        backRightMotor.power = (-y + x - rx) * 0.6

//        if(!previousX && gamepad1.x){
//            triggerTarget -= 2.5
//        }
//
//        previousX = gamepad1.x
//
//        if(ma3Wrapped.getValue() > triggerTarget) {
//            trigger.setPower(0.2)
//        }
//        else {
//            trigger.setPower(0.0)
//        }

        if (!previousY && gamepad1.y) {
            timer.reset()
        }

        when {
            timer.time() > 1.75 -> trigger.setPosition(0.2)
            timer.time() > 1.4 -> trigger.setPosition(0.6)
            timer.time() > 1.05 -> trigger.setPosition(0.2)
            timer.time() > 0.7 -> trigger.setPosition(0.6)
            timer.time() > 0.35 -> trigger.setPosition(0.2)
            timer.time() > 0.0 -> trigger.setPosition(0.6)
        }

        previousY = gamepad1.y

        if (!previousA && gamepad1.a) {
            enabled = !enabled
        }

        previousA = gamepad1.a

        if (!enabled) {
            intakeMotor.power = 0.7
            return
        }

        intakeMotor.power = 0.0

        telemetry.addData("prevB", previousB)
        telemetry.addData("b", gamepad1.b)
        if (!previousB && gamepad1.b) {
            enabledB = !enabledB
        }

        previousB = gamepad1.b
        if (!enabledB) {
            outerShooterMotor.velocity = 2000.0
            innerShooterMotor.velocity = 2000.0
            return
        }

        outerShooterMotor.velocity = targetVelocity
        innerShooterMotor.velocity = targetVelocity

//        targetVelocity += gamepad1.right_trigger.toDouble() * speed
//        targetVelocity -= gamepad1.left_trigger.toDouble() * speed
        telemetry.addData("time", timer.seconds())
        telemetry.addData("target", trigger.getPosition())
        telemetry.addData("y", gamepad1.y)
//        telemetry.addData("ma3Raw", ma3.voltage)
//        telemetry.addData("ma3Wrapped", ma3Wrapped.getValue())
        telemetry.addData("triggerTarget", triggerTarget)
        telemetry.addData("FLP", frontLeftMotor.power)
        telemetry.addData("FRP", frontRightMotor.power)
        telemetry.addData("BLP", backLeftMotor.power)
        telemetry.addData("BRP", backRightMotor.power)
        telemetry.addData("intake_motor", intakeMotor.power)
        telemetry.addData("a", gamepad1.a)
        telemetry.addData("enabled", enabled)
        telemetry.addData("b", gamepad1.b)
        telemetry.addData("enabledB", enabledB)
        telemetry.addData("outerVelocity", outerShooterMotor.velocity)
        telemetry.addData("innerVelocity", innerShooterMotor.velocity)
        telemetry.addData("targetVelocity", targetVelocity)
        telemetry.addData("right_stick_y", gamepad1.right_stick_y)
    }


    var targetVelocity = 0.0
    val speed = 10

    var previousA = false
    var enabled = true
    var previousB = false
    var enabledB = true

    //        var previousX = false
//        var enabledX = true
    var previousY = false
    var enabledY = true

}