package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.Colors.BLUE
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.Colors.RED
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

object Colors {
    val RED = Scalar(255.0, 0.0, 0.0)
    val BLUE = Scalar(0.0, 0.0, 255.0)
}

class MOEHighGoalPipeline : MOEPipeline() {
    var lowerRed = 155.0
    var upperRed = 255.0
    var lowerBlue = 155.0
    var upperBlue = 255.0
    private val matYCrCb = Mat()

    private val redChannel = Mat()
    private val blueChannel = Mat()

    private val redContours = mutableListOf<MatOfPoint>()
    private val blueContours = mutableListOf<MatOfPoint>()

    var blueRect = Rect()
    var redRect = Rect()

    private val redThreshold = Mat()
    private val blueThreshold = Mat()

    private val identifyBlue = true
    private val identifyRed = false
    private var biggestBlueContour: MatOfPoint? = null
    private var biggestRedContour: MatOfPoint? = null
    private fun findRect(input: Mat) {
        Imgproc.cvtColor(input, matYCrCb, Imgproc.COLOR_RGB2YCrCb)
        if (identifyRed) {
            Core.extractChannel(matYCrCb, redChannel, 1)
            Imgproc.threshold(redChannel, redThreshold, lowerRed, upperRed, Imgproc.THRESH_BINARY)
            redContours.clear()
            Imgproc.findContours(redThreshold, redContours, Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
        }

        if (identifyBlue) {
            Core.extractChannel(matYCrCb, blueChannel, 2)
            Imgproc.threshold(blueChannel, blueThreshold, lowerBlue, upperBlue, Imgproc.THRESH_BINARY)
            blueContours.clear()
            Imgproc.findContours(blueThreshold, blueContours, Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
        }
        biggestBlueContour = blueContours.maxByOrNull { Imgproc.contourArea(it) }
        biggestRedContour = redContours.maxByOrNull { Imgproc.contourArea(it) }

    }

    override fun preview(input: Mat): Mat {
        findRect(input)
        val biggestBlueContour = blueContours.maxByOrNull { Imgproc.contourArea(it) }
        if (biggestBlueContour != null) {
            blueRect = Imgproc.boundingRect(biggestBlueContour)
            input.drawRect(blueRect, BLUE)
        }

        val biggestRedContour = redContours.maxByOrNull { Imgproc.contourArea(it) }
        if (biggestRedContour != null) {
            redRect = Imgproc.boundingRect(biggestRedContour)
            input.drawRect(redRect, RED)
        }
        return input
    }

    override fun process(input: Mat): Mat {

        if (biggestBlueContour != null) blueRect = Imgproc.boundingRect(biggestBlueContour)

        if (biggestRedContour != null) redRect = Imgproc.boundingRect(biggestRedContour)
        return input
    }
}