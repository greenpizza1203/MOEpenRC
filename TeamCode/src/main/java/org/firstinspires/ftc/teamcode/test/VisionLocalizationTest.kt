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

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.MOEHighGoalPipeline
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCVConfig
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.centerX
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.Target
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOERawPid
import org.firstinspires.ftc.teamcode.teleop.UltimateGoalTeleOp
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

@TeleOp
class VisionLocalizationTest : UltimateGoalTeleOp() {
    val turnPid = MOERawPid(0.003, 0.0, 0.0)

    override fun initOpMode() {
        super.initOpMode()
        turnPid.setOutputLimits(0.4)

    }

    override fun initChassis() {
        robot.chassis.loop {
            val driveVector = gpad1.left.stick.vector()

            //Field centric driving
            driveVector.rotate(-robot.gyro.angle)

            val blueRect = (robot.opencv.pipeline as MOEHighGoalPipeline).blueRect


            val rot = when {
                gpad1.dpad.left() -> gpad1.right.stick.x()
                blueRect != null -> -turnPid.getOutput(blueRect.centerX(), 320.0)
                else -> 0.0
            }
            telemetry.addData("rot", rot)
//            telemetry.addData("middleBlue", middleBlue)
            setFromPolar(driveVector, rot)
        }
    }

    override fun mainLoop() {


    }

    override val initialPose = Pose2d(heading = 270.toRadians())

    override val openCVConfig = MOEPenCVConfig(MOEHighGoalPipeline(Target.RED))

}


