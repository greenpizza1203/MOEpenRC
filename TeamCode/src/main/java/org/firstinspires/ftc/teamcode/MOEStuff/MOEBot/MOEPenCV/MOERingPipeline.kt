package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import org.opencv.core.*
import org.opencv.imgproc.Imgproc


class MOERingPipeline : MOEPipeline() {
    val lowH = 0.0
    val highH = 180.0
//    val how
//    sliderS = sliderPanel.add("Saturation", 0, 255)
//    sliderV = sliderPanel.add("Value", high = 255)
//    var blueRect = Rect()
//    var redRect = Rect()

    val frameHSV = Mat()
    val thresh = Mat()
    val submat = Rect(10, 10, 100, 100)
    override fun preview(input: Mat): Mat {
        //TODO: the stuff
//        val submat = Mat(input, submat)
        return input
    }


    override fun process(input: Mat): Mat {
        Imgproc.cvtColor(input, frameHSV, Imgproc.COLOR_RGB2HSV)
        Core.inRange(frameHSV,
                Scalar(lowH, 0.0, 0.0),
                Scalar(highH, 0.0, 0.0),
                thresh)
        return frameHSV
//        update(frame, thresh)
    }
}