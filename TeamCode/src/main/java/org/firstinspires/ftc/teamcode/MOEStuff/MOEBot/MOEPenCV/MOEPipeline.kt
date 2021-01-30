package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.opencv.core.Mat
import org.openftc.easyopencv.OpenCvPipeline

open class MOEPipeline : OpenCvPipeline() {
    var lastFrame: Mat? = null
    var frameRequested = false

    init {
        MEMLEAK_DETECTION_ENABLED = false;
    }

    final override fun processFrame(input: Mat): Mat {
        if (frameRequested) {
            lastFrame = input
            frameRequested = false
        }
//        var processed = process(input)
        return if (!moeOpMode.isActive()) preview(input) else process(input)
//        val pre = preProcess(input)
    }

    open fun preview(input: Mat): Mat {
        return input
    }

    open fun process(input: Mat): Mat {
        return input
    }
//    private fun drawText(newMat: Mat) {
//        val croppedMat = Mat()
//        Imgproc.resize(newMat, croppedMat, Size(4.0, 1.0))
//        Imgproc.cvtColor(croppedMat, croppedMat, COLOR_BGR2HSV)
//        val data = processData(croppedMat)
//
//        data.forEachIndexed { index, it ->
//            newMat.drawText(60.0 + 300 * index, 295.0, it.roundToInt().toString(), color = PINK)
//        }
//
//    }

    private fun processData(croppedMat: Mat): MutableList<Double> {
        val data = MutableList(4) {
            croppedMat.get(0, it)[2]
        }

        data[1] = (data[1] + data[2]) / 2.0
        data.removeAt(2)
        return data
    }


    fun requestFrame() {
        frameRequested = true
    }



}