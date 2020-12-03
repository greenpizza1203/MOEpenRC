package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.hardware.lynx.LynxModule.BulkCachingMode.AUTO;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.sin;

@TeleOp
public class ExtremeFastTeleOp extends OpMode {
    double gyroOffset = Math.toRadians(90);
    BNO055IMU gyro;
    DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    DcMotor intakeMotor;
    DcMotorEx outerShooterMotor, innerShooterMotor;
    ElapsedTime timer = new ElapsedTime();
    Servo trigger;

    @Override
    public void init() {

        gyro = hardwareMap.get(BNO055IMU.class, "imu");
        gyro.initialize(new BNO055IMU.Parameters());
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "FLM20");
        frontLeftMotor.setZeroPowerBehavior(BRAKE);
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "FRM22");
        frontRightMotor.setZeroPowerBehavior(BRAKE);

        backLeftMotor = hardwareMap.get(DcMotorEx.class, "BLM21");
        backLeftMotor.setZeroPowerBehavior(BRAKE);

        backRightMotor = hardwareMap.get(DcMotorEx.class, "BRM23");
        backRightMotor.setZeroPowerBehavior(BRAKE);
        frontLeftMotor.setDirection(REVERSE);
        backLeftMotor.setDirection(REVERSE);

        intakeMotor = hardwareMap.dcMotor.get("INM13");
        intakeMotor.setZeroPowerBehavior(BRAKE);
        outerShooterMotor = hardwareMap.get(DcMotorEx.class, "OFM10");
        innerShooterMotor = hardwareMap.get(DcMotorEx.class, "IFM11");
        trigger = hardwareMap.servo.get("RTS25");
        trigger.setPosition(0.2);
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(AUTO);
        }

    }

    @Override
    public void init_loop() {
        if (!gyro.isGyroCalibrated()) {
            telemetry.addData("Initializing", "Gyro");
        } else {
            telemetry.addData("Initializing", "Complete");
        }
    }

    double oldFwd;
    double oldStr;
    double oldRot;

    @Override
    public void loop() {
        long startTime = System.nanoTime();
        handleToggles();
        if (gamepad1.right_stick_button) gyroOffset = 90 + gyro.getAngularOrientation().firstAngle;

        double y = -gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x;
        double angle = gyro.getAngularOrientation().firstAngle - gyroOffset;

        double s = sin(angle);
        double c = cos(angle);
        //Field centric driving
        double fwd = x * c - y * s;
        double str = x * s + y * c;
        double rot = gamepad1.right_stick_x;
        if (fwd != oldFwd || str != oldStr || rot != oldRot) {
            double flp = fwd + str + rot;
            double frp = fwd - str - rot;
            double blp = fwd - str + rot;
            double brp = fwd + str - rot;
            double max = max(1, max(flp, max(frp, max(blp, brp))));
            frontLeftMotor.setPower(flp / max);
            frontRightMotor.setPower(frp / max);
            backLeftMotor.setPower(blp / max);
            backRightMotor.setPower(brp / max);
        }
        oldFwd = fwd;
        oldStr = str;
        oldRot = rot;
        double time = timer.time();
        if (!(time > 2.1)) {
            if (time > 1.75) trigger.setPosition(0.2);
            else if (time > 1.4) trigger.setPosition(0.6);
            else if (time > 1.05) trigger.setPosition(0.2);
            else if (time > 0.7) trigger.setPosition(0.6);
            else if (time > 0.35) trigger.setPosition(0.2);
            else if (time > 0.0) trigger.setPosition(0.6);
        }

        telemetry.addData("Loop ms", (System.nanoTime() - startTime) / 1000000.0);

    }

    double shooterTarget = 2000.0;


    boolean oldA;
    boolean oldY;
    boolean yToggled;
    boolean oldX;

    public void handleToggles() {
        if (gamepad1.a && !oldA) {
            intakeMotor.setPower(intakeMotor.getPower() == 0.0 ? 1.0 : 0.0);
        }
        oldA = gamepad1.a;
        if (gamepad1.y && !oldY) {
            yToggled = !yToggled;
            if (yToggled) {
                outerShooterMotor.setVelocity(shooterTarget);
                innerShooterMotor.setVelocity(shooterTarget);
            } else {
                outerShooterMotor.setPower(0.0);
                innerShooterMotor.setPower(0.0);
            }
        }
        oldY = gamepad1.y;
        if (gamepad1.x && !oldX) timer.reset();
        oldX = gamepad1.x;
    }

}