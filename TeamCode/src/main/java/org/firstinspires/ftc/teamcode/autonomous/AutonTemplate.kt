package org.firstinspires.ftc.teamcode.autonomous

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEAutonArm.MOEAutonArm
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEBotSubSystemConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEGyroConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEOpenCVConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton
import org.firstinspires.ftc.teamcode.autonomous.sideconfig.AutonSideConfig
import org.firstinspires.ftc.teamcode.autonomous.sideconfig.Movement
import org.firstinspires.ftc.teamcode.autonomous.vision.SkyStoneLocation
import org.firstinspires.ftc.teamcode.autonomous.vision.getSkyStoneLocationFromBitmap
import org.firstinspires.ftc.teamcode.teleop.CompTeleOp
import org.firstinspires.ftc.teamcode.utilities.internal.initOtherOpMode
import org.firstinspires.ftc.teamcode.utilities.internal.wait

//@Autonomous
open class AutonTemplate(val config: AutonSideConfig) : MOEAuton() {
    lateinit var autonArms: MOEAutonArm
    override fun initOpMode() {
//     autonArms.raiseArm()
//     autonArms.openClaw()
        autonArms = if (config.negateStuff) robot.autonArms.left else robot.autonArms.right

        robot.autonArms.initAutonArms()
        robot.autonArms.moveToShowCamera()
        robot.foundation.moveUp()
    }

    override fun run() {
        val location = getSkyStoneLocation()
//        var location = SkyStoneLocation.LEFT

        robot.autonArms.initAutonArms()
        telemetry.addData("location", location)
        telemetry.addData("gyro", robot.gyro.angle)
        telemetry.update()
        val distances = config.getDistances(location)
        //FROM WALL TO STONES
        move(distances[0])
        robot.chassis.turnTo(90.0)
        //GRAB 1ST SKYSTONE
        move(distances[1])
        wait(300)
        move(distances[2])
        autonArms.grabStone()
        autonArms.liftStone()
        move(distances[3])
        robot.chassis.turnTo(90.0)
        //PLACE 1ST STONE ON FOUNDATION
        move(distances[4])
        wait(300)
        move(distances[5])
        autonArms.dropStone()
        autonArms.raiseArm()
        autonArms.initAutonArm()
        robot.chassis.turnTo(90.0)
        move(distances[6])
        //GRAB 2ND SKYSTONE
        robot.chassis.turnTo(90.0)
        move(distances[7])
        wait(300)
        move(distances[8])
        autonArms.grabStone()
        autonArms.liftStone()
        move(distances[9])
        robot.chassis.turnTo(90.0)
        //PLACE 2ND STONE ON FOUNDATION
        move(distances[10])
        wait(300)
        move(distances[11])
        autonArms.dropStone()
        autonArms.raiseArm()
        autonArms.initAutonArm()
        robot.chassis.turnTo(90.0)
        move(distances[12])
        robot.chassis.turnTo(90.0)
        //PARK
        move(distances[13])
        robot.chassis.stop()
        waitForStop()
    }

    override fun postRun() {
//        PrefsHandler.setDouble(AUTON_GYRO_FINAL_ANGLE, robot.gyro.angle + if (config.negateStuff) 180 else 0)
        initOtherOpMode(CompTeleOp::class)
    }

    private fun move(movement: Movement) {
        robot.chassis.encoders.move(movement)
    }


    private fun getSkyStoneLocation(): SkyStoneLocation {
//        val bm = robot.vuforia.getCroppedBitmap(AutonConstants.Skystone.SKYSTONE_CROP)!!
        return getSkyStoneLocationFromBitmap(robot.opencv.getBitmap(), config.cropRectangle)
    }

    override fun getRobotSubSystemConfig(): MOEBotSubSystemConfig {
        return super.getRobotSubSystemConfig().apply {
            useOpenCV = true
            useGyro = true
            useVuforia = false
            useOdometry = false
        }
    }

    override fun getOpenCVConfig(): MOEOpenCVConfig {
        return super.getOpenCVConfig().apply {
            useInternalCamera = false
            processExtra = false
            drawOverlay = true
            autonConfig = config

        }
    }

    override fun getGyroConfig(): MOEGyroConfig {
        return super.getGyroConfig().apply { initalAng = if (config.negateStuff) 180.0 else 0.0 }
    }
}
