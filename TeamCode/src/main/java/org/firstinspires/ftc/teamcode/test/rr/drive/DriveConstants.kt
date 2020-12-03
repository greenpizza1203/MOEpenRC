package org.firstinspires.ftc.teamcode.test.rr.drive

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints

/*
 * Constants shared between multiple drive types.
 *
 * TODO: Tune or adjust the following constants to fit your robot. Note that the non-final
 * fields may also be edited through the dashboard (connect to the robot's WiFi network and
 * navigate to https://192.168.49.1:8080/dash). Make sure to save the values here after you
 * adjust them in the dashboard; **config variable changes don't persist between app restarts**.
 *
 * These are not the only parameters; some are located in the localizer classes, drive base classes,
 * and op modes themselves.
 */
@Config
object DriveConstants {
    const val MAX_RPM = 312.0

    /*
     * These are physical constants that can be determined from your robot (including the track
     * width; it will be tune empirically later although a rough estimate is important). Users are
     * free to chose whichever linear distance unit they would like so long as it is consistently
     * used. The default values were selected with inches in mind. Road runner uses radians for
     * angular distances although most angular parameters are wrapped in Math.toRadians() for
     * convenience. Make sure to exclude any gear ratio included in MOTOR_CONFIG from GEAR_RATIO.
     */
    private var WHEEL_RADIUS = 1.9685 // in
    var GEAR_RATIO = 1 // output (wheel) speed / input (motor) speed

    @JvmStatic
    var TRACK_WIDTH = 15.60 // in

    /*
     * These are the feedforward parameters used to model the drive motor behavior. If you are using
     * the built-in velocity PID, *these values are fine as is*. However, if you do not have drive
     * motor encoders or have elected not to use them for velocity control, these values should be
     * empirically tuned.
     */
    @JvmField
    var kV = 180 * 0.0001

    @JvmField
    var kA = 20 * 0.0001

    @JvmField
    var kStatic =0.0

    /*
     * These values are used to generate the trajectories for you robot. To ensure proper operation,
     * the constraints should never exceed ~80% of the robot's actual capabilities. While Road
     * Runner is designed to enable faster autonomous motion, it is a good idea for testing to start
     * small and gradually increase them later after everything is working. The velocity and
     * acceleration values are required, and the jerk values are optional (setting a jerk of 0.0
     * forces acceleration-limited profiling). All distance units are inches.
     */
    @JvmField
    var BASE_CONSTRAINTS = DriveConstraints(
            24.0, 20.0, 0.0,
            Math.toRadians(60.0), Math.toRadians(180.0), 0.0
    )

    @JvmStatic
    fun rpmToVelocity(rpm: Double): Double {
        val wheelCircumference = 2 * Math.PI * WHEEL_RADIUS
        return rpm * GEAR_RATIO * wheelCircumference / 60.0
    }
}

fun main() {

    println(DriveConstants.kV)
}