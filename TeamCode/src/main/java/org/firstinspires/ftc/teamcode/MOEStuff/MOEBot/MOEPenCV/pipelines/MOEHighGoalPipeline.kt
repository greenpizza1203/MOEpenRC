package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.*
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.Colors.BLUE
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.Colors.RED
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import kotlin.math.roundToInt


enum class Target {
    RED, BLUE, BOTH
}

class MOEHighGoalPipeline(target: Target) : MOEPipeline() {
    var lowerRed = 155.0
    var upperRed = 255.0
    var lowerBlue = 155.0
    var upperBlue = 255.0
    private val matYCrCb = Mat()

    private val redChannel = Mat()
    private val blueChannel = Mat()

    private val redContours = mutableListOf<MatOfPoint>()
    private val blueContours = mutableListOf<MatOfPoint>()

    var blueRect: Rect? = null
    var redRect: Rect? = null

    private val redThreshold = Mat()
    private val blueThreshold = Mat()

    private val identifyBlue = target == Target.BLUE || target == Target.BOTH
    private val identifyRed = target == Target.RED || target == Target.BOTH
    private var biggestBlueContour: MatOfPoint? = null
    private var biggestRedContour: MatOfPoint? = null
    var conversion = if (System.getenv("DESKTOP") != null) {
        Imgproc.COLOR_BGR2YCrCb
    } else {
        Imgproc.COLOR_RGB2YCrCb
    }

    var tempMat = Mat()

    override fun process(input: Mat): Mat {
        Imgproc.cvtColor(input, matYCrCb, conversion)
        if (identifyRed) {
            Core.extractChannel(matYCrCb, redChannel, 1)
            Imgproc.threshold(redChannel, redThreshold, lowerRed, upperRed, Imgproc.THRESH_BINARY)
            redContours.clear()
            Imgproc.findContours(redThreshold, redContours, tempMat, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE)
            biggestRedContour = redContours.maxByOrNull { Imgproc.contourArea(it) }
            redRect = biggestRedContour?.boundingRect()

        }
        if (identifyBlue) {
            Core.extractChannel(matYCrCb, blueChannel, 2)
            Imgproc.threshold(blueChannel, blueThreshold, lowerBlue, upperBlue, Imgproc.THRESH_BINARY)
            blueContours.clear()
            Imgproc.findContours(blueThreshold, blueContours, tempMat, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
            biggestBlueContour = blueContours.maxByOrNull { Imgproc.contourArea(it) }
            blueRect = biggestBlueContour?.boundingRect()
        }
        return input
    }

    var size: Size? = null
    override fun preview(input: Mat): Mat {
        process(input)
        if (size == null) size = input.size()
        redRect?.let {
            val centerX = (it.x + it.width / 2).toDouble()
            input.drawRect(it, RED)
            input.drawVerticalLine(centerX, RED)
            input.drawText(size!!.width * 0.8, size!!.height * 0.95, centerX.roundToInt().toString(), color = RED)
        }
        blueRect?.let {
            val centerX = (it.x + it.width / 2).toDouble()
            input.drawRect(it, BLUE)
            input.drawVerticalLine(centerX, BLUE)

        }
        return input
    }
}