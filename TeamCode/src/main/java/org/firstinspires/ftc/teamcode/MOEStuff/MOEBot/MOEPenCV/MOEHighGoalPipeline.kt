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
    private val redThreshold = Mat()
    private val matYCrCb = Mat()
    private val redChannel = Mat()
    private val blueChannel = Mat()
    private val redContours = mutableListOf<MatOfPoint>()
    private val blueContours = mutableListOf<MatOfPoint>()

    var blueRect = Rect()
    var redRect = Rect()
    private val blueThreshold = Mat()

    private val redHeatmap = Mat()
    private val blueHeatmap = Mat()
    val identifyBlue = true
    val identifyRed = false
    override fun preProcess(input: Mat): Mat {
        Imgproc.cvtColor(input, matYCrCb, Imgproc.COLOR_RGB2YCrCb)
        Core.extractChannel(matYCrCb, redChannel, 1)
        if (identifyRed) {
            Imgproc.threshold(redChannel, redThreshold, lowerRed, upperRed, Imgproc.THRESH_BINARY)
            redContours.clear()
            Imgproc.findContours(redThreshold, redContours, Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
            Core.inRange(redChannel, Scalar(lowerRed), Scalar(upperRed), redHeatmap)
        }

        if (identifyBlue) {
            Core.extractChannel(matYCrCb, blueChannel, 2)
            Imgproc.threshold(blueChannel, blueThreshold, 155.0, 255.0, Imgproc.THRESH_BINARY)
            blueContours.clear()
            Imgproc.findContours(blueThreshold, blueContours, Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
            Core.inRange(blueChannel, Scalar(lowerBlue), Scalar(upperBlue), blueHeatmap)
        }
        return input
    }


    override fun process(input: Mat): Mat {

        val biggestBlueContour = blueContours.maxBy { Imgproc.contourArea(it) }
        if (biggestBlueContour != null) {
            blueRect = Imgproc.boundingRect(biggestBlueContour)
            input.drawRect(blueRect, BLUE)

        }

        val biggestRedContour = redContours.maxBy { Imgproc.contourArea(it) }
        if (biggestRedContour != null) {
            redRect = Imgproc.boundingRect(biggestRedContour)
            input.drawRect(redRect, RED)
        }
        return input
    }
}