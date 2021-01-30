package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis

import com.acmerobotics.roadrunner.drive.Drive
import com.acmerobotics.roadrunner.drive.DriveSignal
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.kinematics.Kinematics
import com.acmerobotics.roadrunner.kinematics.MecanumKinematics
import com.acmerobotics.roadrunner.localization.Localizer
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumConstraints
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro.MOEGyro
import org.firstinspires.ftc.teamcode.test.rr.drive.DriveConstants
import org.firstinspires.ftc.teamcode.test.rr.drive.DriveConstants.TRACK_WIDTH
import org.firstinspires.ftc.teamcode.test.rr.drive.DriveConstants.kA
import org.firstinspires.ftc.teamcode.test.rr.drive.DriveConstants.kStatic
import org.firstinspires.ftc.teamcode.test.rr.drive.DriveConstants.kV
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive.Companion.HEADING_PID
import org.firstinspires.ftc.teamcode.test.rr.drive.SampleMecanumDrive.Companion.TRANSLATIONAL_PID

class MOERunner(val gyro: MOEGyro, var chassis: MOEChassis) : Drive() {
    val follower = HolonomicPIDVAFollower(TRANSLATIONAL_PID, TRANSLATIONAL_PID, HEADING_PID)

    override var localizer: Localizer = MOEThreeWheelOdo()

    val constraints = MecanumConstraints(DriveConstants.BASE_CONSTRAINTS, TRACK_WIDTH)

    public override val rawExternalHeading: Double
        get() = gyro.getRawAngle()

    override fun setDriveSignal(driveSignal: DriveSignal) {
        val velocities = MecanumKinematics.robotToWheelVelocities(driveSignal.vel, TRACK_WIDTH, TRACK_WIDTH, 1.0)
        val accelerations = MecanumKinematics.robotToWheelAccelerations(driveSignal.accel, TRACK_WIDTH, TRACK_WIDTH, 1.0)
        val powers = Kinematics.calculateMotorFeedforward(velocities, accelerations, kV, kA, kStatic)
        chassis.setPower(powers[0], powers[3], powers[1], powers[2])
    }

    override fun setDrivePower(drivePower: Pose2d) {
        val powers = MecanumKinematics.robotToWheelVelocities(drivePower, 1.0, 1.0, 1.0)
        chassis.setPower(powers[0], powers[3], powers[1], powers[2])
    }

    fun trajectory(start: Pose2d = localizer.poseEstimate, func: TrajectoryBuilder.() -> Unit): Trajectory {
        val builder = TrajectoryBuilder(start, constraints = constraints)
        builder.func()
        return builder.build()
    }
}

