package org.firstinspires.ftc.teamcode.test;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.spartronics4915.lib.T265Camera;

import java.lang.annotation.Annotation;

@TeleOp
public class T265Test extends OpMode {


    @Override
    public void init() {
        // This is the transformation between the center of the camera and the center of the robot
        Transform2d cameraToRobot = new Transform2d();
        // Increase this value to trust encoder odometry less when fusing encoder measurements with VSLAM
        double encoderMeasurementCovariance = 0.8;
        // Set to the starting pose of the robot
        Pose2d startingPose = new Pose2d(0, 0, new Rotation2d());
        Transform2d robotOffsetMeters = new Transform2d();
        T265Camera slamra = new T265Camera(cameraToRobot, encoderMeasurementCovariance, hardwareMap.appContext);
        slamra.setPose(startingPose); // Useful if your robot doesn't start at the field-relative origin

// Call this when you're ready to get camera updates
        slamra.start();

// Now we can grab our last received pose in our main thread
        while (true) {
            slamra.getLastReceivedCameraUpdate();
        }
    }

    @Override
    public void loop() {

    }
}
