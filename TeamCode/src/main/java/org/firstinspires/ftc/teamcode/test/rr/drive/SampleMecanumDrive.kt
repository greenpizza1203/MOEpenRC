package org.firstinspires.ftc.teamcode.test.rr.drive

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.control.PIDCoefficients
import com.acmerobotics.roadrunner.control.PIDFController
import com.acmerobotics.roadrunner.drive.DriveSignal
import com.acmerobotics.roadrunner.drive.MecanumDrive
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower
import com.acmerobotics.roadrunner.followers.TrajectoryFollower
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.profile.MotionProfile
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator.generateSimpleMotionProfile
import com.acmerobotics.roadrunner.profile.MotionState
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumConstraints
import com.acmerobotics.roadrunner.util.NanoClock
import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.test.rr.util.DashboardUtil
import org.firstinspires.ftc.teamcode.test.rr.util.LynxModuleUtil
import java.util.*

/*
* Simple mecanum drive hardware implementation for REV hardware.
*/
@Config
class SampleMecanumDrive(hardwareMap: HardwareMap) : MecanumDrive(DriveConstants.kV, DriveConstants.kA, DriveConstants.kStatic, DriveConstants.TRACK_WIDTH, DriveConstants.TRACK_WIDTH, LATERAL_MULTIPLIER) {
    enum class Mode {
        IDLE, TURN, FOLLOW_TRAJECTORY
    }

    private val dashboard = FtcDashboard.getInstance()
    private val telemetry = dashboard.telemetry
    private val clock: NanoClock
    var mode: Mode
    private val turnController: PIDFController
    private var turnProfile: MotionProfile? = null
    private var turnStart = 0.0
    private val constraints: DriveConstraints
    private val follower: TrajectoryFollower
    private val poseHistory: MutableList<Pose2d>
    private val frontLeft: DcMotorEx
    private val backLeft: DcMotorEx
    private val backRight: DcMotorEx
    private val frontRight: DcMotorEx
    private val motors: List<DcMotorEx>
    private val imu: BNO055IMU
    private var lastPoseOnTurn: Pose2d? = null
    fun trajectoryBuilder(startPose: Pose2d): TrajectoryBuilder {
        return TrajectoryBuilder(startPose, constraints = constraints)
    }

    fun trajectoryBuilder(startPose: Pose2d?, reversed: Boolean): TrajectoryBuilder {
        return TrajectoryBuilder(startPose!!, reversed, constraints)
    }

    fun trajectoryBuilder(startPose: Pose2d?, startHeading: Double): TrajectoryBuilder {
        return TrajectoryBuilder(startPose!!, startHeading, constraints)
    }

    fun turnAsync(angle: Double) {
        val heading = poseEstimate.heading
        lastPoseOnTurn = poseEstimate
        turnProfile = generateSimpleMotionProfile(
                MotionState(heading, 0.0, 0.0, 0.0),
                MotionState(heading + angle, 0.0, 0.0, 0.0),
                constraints.maxAngVel,
                constraints.maxAngAccel,
                constraints.maxAngJerk
        )
        turnStart = clock.seconds()
        mode = Mode.TURN
    }

    fun turn(angle: Double) {
        turnAsync(angle)
        waitForIdle()
    }

    fun followTrajectoryAsync(trajectory: Trajectory?) {
        follower.followTrajectory(trajectory!!)
        mode = Mode.FOLLOW_TRAJECTORY
    }

    fun followTrajectory(trajectory: Trajectory?) {
        followTrajectoryAsync(trajectory)
        waitForIdle()
    }

    val lastError: Pose2d
        get() {
            return when (mode) {
                Mode.FOLLOW_TRAJECTORY -> follower.lastError
                Mode.TURN -> Pose2d(0.0, 0.0, turnController.lastError)
                Mode.IDLE -> Pose2d()
            }
        }

    fun update() {
        updatePoseEstimate()
        val currentPose = poseEstimate
        val lastError = lastError
        poseHistory.add(currentPose)
        val packet = TelemetryPacket()
        val fieldOverlay = packet.fieldOverlay()
        packet.put("mode", mode)
        packet.put("x", currentPose.x)
        packet.put("y", currentPose.y)
        packet.put("heading", currentPose.heading)
        packet.put("xError", lastError.x)
        packet.put("yError", lastError.y)
        packet.put("headingError", lastError.heading)
        when (mode) {
            Mode.IDLE -> {
            }
            Mode.TURN -> {
                val t = clock.seconds() - turnStart
                val targetState = turnProfile!![t]
                turnController.targetPosition = targetState.x
                val correction = turnController.update(currentPose.heading)
                val targetOmega = targetState.v
                val targetAlpha = targetState.a
                setDriveSignal(DriveSignal(Pose2d(
                        0.0, 0.0, targetOmega + correction
                ), Pose2d(
                        0.0, 0.0, targetAlpha
                )))
                val newPose = lastPoseOnTurn!!.copy(lastPoseOnTurn!!.x, lastPoseOnTurn!!.y, targetState.x)
                fieldOverlay.setStroke("#4CAF50")
                DashboardUtil.drawRobot(fieldOverlay, newPose)
                if (t >= turnProfile!!.duration()) {
                    mode = Mode.IDLE
                    setDriveSignal(DriveSignal())
                }
            }
            Mode.FOLLOW_TRAJECTORY -> {
                setDriveSignal(follower.update(currentPose))
                val trajectory = follower.trajectory
                fieldOverlay.setStrokeWidth(1)
                fieldOverlay.setStroke("#4CAF50")
                DashboardUtil.drawSampledPath(fieldOverlay, trajectory.path)
                val t = follower.elapsedTime()
                DashboardUtil.drawRobot(fieldOverlay, trajectory[t])
                fieldOverlay.setStroke("#3F51B5")
                DashboardUtil.drawPoseHistory(fieldOverlay, poseHistory)
                if (!follower.isFollowing()) {
                    mode = Mode.IDLE
                    setDriveSignal(DriveSignal())
                }
            }
        }
        fieldOverlay.setStroke("#3F51B5")
        DashboardUtil.drawRobot(fieldOverlay, currentPose)
        dashboard.sendTelemetryPacket(packet)
    }

    fun waitForIdle() {
        while (!Thread.currentThread().isInterrupted && isBusy) {
            update()
        }
    }

    val isBusy: Boolean
        get() = mode != Mode.IDLE

    fun setMode(runMode: RunMode?) {
        for (motor in motors) {
            motor.mode = runMode
        }
    }

    fun setZeroPowerBehavior(zeroPowerBehavior: ZeroPowerBehavior?) {
        for (motor in motors) {
            motor.zeroPowerBehavior = zeroPowerBehavior
        }
    }

    override fun getWheelPositions(): List<Double> {
        return listOf(0.0, 0.0, 0.0, 0.0)
        //        List<Double> wheelPositions = new ArrayList<>();
//        for (DcMotorEx motor : motors) {
//            wheelPositions.add(encoderTicksToInches(motor.getCurrentPosition()));
//        }
//        return wheelPositions;
    }

    override fun getWheelVelocities(): List<Double>? {
        return null
        //        List<Double> wheelVelocities = new ArrayList<>();
//        for (DcMotorEx motor : motors) {
//            wheelVelocities.add(encoderTicksToInches(motor.getVelocity()));
//        }
//        return wheelVelocities;
    }

    override fun setMotorPowers(frontLeft: Double, rearLeft: Double, rearRight: Double, frontRight: Double) {
//        FtcDashboard.getInstance()
//        telemetry.addData("frontLeft", v)
//        telemetry.addData("backLeft", v1)
//        telemetry.addData("backRight", v2)
//        telemetry.addData("frontRight", v3)
//        telemetry.update()
//        return;
        this.frontLeft.power = frontLeft
        this.backLeft.power = rearLeft
        this.backRight.power = rearRight
        this.frontRight.power = frontRight
    }

    override val rawExternalHeading: Double
        get() = imu.angularOrientation.firstAngle.toDouble()

    companion object {
        var TRANSLATIONAL_PID = PIDCoefficients(0.0, 0.0, 0.0)
        var HEADING_PID = PIDCoefficients(0.1, 0.0, 0.0)
        var LATERAL_MULTIPLIER = 1.0
    }

    init {
        dashboard.telemetryTransmissionInterval = 25
        clock = NanoClock.system()
        mode = Mode.IDLE
        turnController = PIDFController(HEADING_PID)
        turnController.setInputBounds(0.0, 2 * Math.PI)
        constraints = MecanumConstraints(DriveConstants.BASE_CONSTRAINTS, DriveConstants.TRACK_WIDTH)
        follower = HolonomicPIDVAFollower(TRANSLATIONAL_PID, TRANSLATIONAL_PID, HEADING_PID,
                Pose2d(0.5, 0.5, Math.toRadians(5.0)), 0.5)
        poseHistory = ArrayList()
        LynxModuleUtil.ensureMinimumFirmwareVersion(hardwareMap)
        for (module in hardwareMap.getAll(LynxModule::class.java)) {
            module.bulkCachingMode = LynxModule.BulkCachingMode.AUTO
        }

        // TODO: adjust the names of the following hardware devices to match your configuration
        imu = hardwareMap.get(BNO055IMU::class.java, "imu")
        val parameters = BNO055IMU.Parameters()
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS
        imu.initialize(parameters)

        // TODO: if your hub is mounted vertically, remap the IMU axes so that the z-axis points
        // upward (normal to the floor) using a command like the following:
        // BNO055IMUUtil.remapAxes(imu, AxesOrder.XYZ, AxesSigns.NPN);
        frontLeft = hardwareMap.get(DcMotorEx::class.java, "FLDM10")
        backLeft = hardwareMap.get(DcMotorEx::class.java, "BLDM11")
        backRight = hardwareMap.get(DcMotorEx::class.java, "BRDM12")
        frontRight = hardwareMap.get(DcMotorEx::class.java, "FRDM13")
        motors = listOf(frontLeft, backLeft, backRight, frontRight)
        for (motor in motors) {
            val motorConfigurationType = motor.motorType.clone()
            motorConfigurationType.achieveableMaxRPMFraction = 1.0
            motor.motorType = motorConfigurationType
        }
        setZeroPowerBehavior(ZeroPowerBehavior.BRAKE)

        // TODO: reverse any motors using DcMotor.setDirection()
        frontLeft.direction = DcMotorSimple.Direction.REVERSE
        backLeft.direction = DcMotorSimple.Direction.REVERSE
        // TODO: if desired, use setLocalizer() to change the localization method
        localizer = StandardTrackingWheelLocalizer(hardwareMap)
    }
}