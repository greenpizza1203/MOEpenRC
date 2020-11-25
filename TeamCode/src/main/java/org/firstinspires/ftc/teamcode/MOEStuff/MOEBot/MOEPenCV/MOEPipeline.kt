package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCVConstants.GREEN
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPenCVConstants.PINK
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.COLOR_BGR2HSV
import org.openftc.easyopencv.OpenCvPipeline
import kotlin.math.roundToInt

class MOEPipeline(private val instance: MOEPenCV) : OpenCvPipeline() {
    var lastFrame: Mat? = null
    var frameRequested = false

    //    lateinit var location: SkyStoneLocation
    override fun processFrame(input: Mat): Mat {
        if (frameRequested) {
            lastFrame = input
            frameRequested = false
        }
//        val subMatrix = input.submat(instance.config.autonConfig.cropRectangle)
//        val newMat = Mat(Size(798.0, 310.0), input.type())
//
//        Imgproc.resize(subMatrix, newMat.submat(Rect(0, 0, 798, 266)), Size(798.0, 266.0))
//
//        if (instance.config.drawOverlay) {
//            drawLines(newMat)
//        }
//        if (instance.config.processExtra) {
//            drawText(newMat)
//        }
//        input.release()

        return input
    }


    private fun drawText(newMat: Mat) {
        val croppedMat = Mat()
        Imgproc.resize(newMat, croppedMat, Size(4.0, 1.0))
        Imgproc.cvtColor(croppedMat, croppedMat, COLOR_BGR2HSV)
        val data = processData(croppedMat)

        data.forEachIndexed { index, it ->
            newMat.drawText(60.0 + 300 * index, 295.0, it.roundToInt().toString(), color = PINK)
        }

    }

    private fun processData(croppedMat: Mat): MutableList<Double> {
        val data = MutableList(4) {
            croppedMat.get(0, it)[2]
        }

        data[1] = (data[1] + data[2]) / 2.0
        data.removeAt(2)
        return data
    }

    private fun drawLines(newMat: Mat) {
        val width = newMat.width().toDouble()
        val height = newMat.height().toDouble()
        Imgproc.line(newMat, Point(width * (1 / 4.0), 0.0), Point(width * (1 / 4.0), height), GREEN, 4)
        Imgproc.line(newMat, Point(width * (3 / 4.0), 0.0), Point(width * (3 / 4.0), height), GREEN, 4)
    }

    fun requestFrame() {
        frameRequested = true

    }

}