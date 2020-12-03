/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEHighGoalPipeline
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOERawPid
import org.firstinspires.ftc.teamcode.teleop.UltimateGoalTeleOp
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.MOEtion
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation

@TeleOp
class VisionLocalizationTest : UltimateGoalTeleOp() {
    lateinit var webcam: OpenCvCamera
    lateinit var pipeline: MOEHighGoalPipeline
    val turnPid = MOERawPid(0.003, 0.0, 0.0)

    override fun initOpMode() {
        super.initOpMode()
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class.java, "Webcam 1"), cameraMonitorViewId)

//        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class, "Webcam 1"));

        pipeline = MOEHighGoalPipeline()
        webcam.setPipeline(pipeline)
        webcam.openCameraDeviceAsync { webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT) }
        turnPid.setOutputLimits(0.4)

    }

    override fun initChassis() {
        robot.chassis.loop {
            val driveVector = gpad1.left.stick.vector()

            //Field centric driving
            driveVector.rotate(-robot.gyro.angle)

            //TODO: pause viewport after init
            val blueRect = pipeline.blueRect
            val middleBlue = blueRect.x + (blueRect.width / 2.0)
            val pidRot = -turnPid.getOutput(middleBlue, 320.0)

            telemetry.addData("rot", pidRot)
            telemetry.addData("middleBlue", middleBlue)

            val rot = if (gpad1.dpad.left()) gpad1.right.stick.x() else pidRot
            setFromPolar(driveVector, rot)
        }
    }

    override fun mainLoop() {


        telemetry.addData("FPS", String.format("%.2f", webcam.fps))
        telemetry.addData("Total frame time ms", webcam.totalFrameTimeMs)
        telemetry.addData("Pipeline time ms", webcam.pipelineTimeMs)
        telemetry.addData("Overhead time ms", webcam.overheadTimeMs)
        telemetry.addData("Theoretical max FPS", webcam.currentPipelineMaxFps)


    }

    override fun getRobotInitialState(): MOEtion {
        return super.getRobotInitialState().also { it.ang = 270.toRadians() }
    }
}


