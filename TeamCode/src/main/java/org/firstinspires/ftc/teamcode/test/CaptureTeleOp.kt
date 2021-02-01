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
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.MOERingPipeline
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.Target
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPid.MOERawPid
import org.firstinspires.ftc.teamcode.teleop.UltimateGoalTeleOp
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toRadians

@TeleOp
class CaptureTeleOp : UltimateGoalTeleOp() {

    override val openCVConfig = MOEPenCVConfig(MOERingPipeline(x = 34, y = 221, width = 95, height = 61))

}


