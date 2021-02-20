package org.firstinspires.ftc.teamcode.autonomous.vision

import android.annotation.SuppressLint
import android.util.Log
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.SwitchableCamera
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.*
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.Target
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.*
import kotlin.math.roundToInt


class MOEPipelineAssist(val hardwareMap: HardwareMap, pipeline: OpenCvPipeline) {
    var webcam: OpenCvSwitchableWebcam
    var ringCam = hardwareMap.get(WebcamName::class.java, "RingCam")
    var highCam = hardwareMap.get(WebcamName::class.java, "HighCam")

    init {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        webcam = OpenCvCameraFactory.getInstance().createSwitchableWebcam(cameraMonitorViewId, ringCam, highCam)
        webcam.setPipeline(pipeline)
        webcam.openCameraDeviceAsync {
            webcam.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT)
        }
    }

}
//
//class TestRingPipeline(val x: Int, val y: Int, val width: Int, val height: Int) : OpenCvPipeline() {
//    @SuppressLint("SdCardPath")
//    override fun init(input: Mat) {
//        val filename = "ring_${System.currentTimeMillis()}"
//        val cropped = input.submat(y, y + height, x, x + width)
//        saveMatToDisk(cropped, "${filename}_cropped")
//        Log.e("file", "saved to $filename")
//    }
//
//    override fun processFrame(input: Mat): Mat {
//        return input.submat(y, y + height, x, x + width)
//
//    }
//
//}


class BasicRingPipeline(val x: Int, val y: Int, val width: Int, val height: Int) : OpenCvPipeline() {
    //    val lowH = 0.0
//    val highH = 180.0
    val lower = Scalar(0.0, 0.0, 0.0)
    val upper = Scalar(40.0, 255.0, 255.0)
//    val how
//    sliderS = sliderPanel.add("Saturation", 0, 255)
//    sliderV = sliderPanel.add("Value", high = 255)
//    var blueRect = Rect()
//    var redRect = Rect()

    val frameHSV = Mat()
    val thresh = Mat()
    val small = Mat()
    var count = 0
//    override fun preview(input: Mat): Mat {
//        val color = (System.currentTimeMillis() / 1000 % 20) < 10
//        val submat = input.submat(y, y + height, x, x + width)
//        //TODO: the stuff
//        Imgproc.cvtColor(submat, frameHSV, Imgproc.COLOR_RGB2HSV)
////        Core.split(frameHSV, channels)
////        Core.extractChannel(frameHSV, hChannel, 0)
////        Imgproc.threshold(hChannel, hChannel, 40.0, 0.0, THRESH_TOZERO)
////        Core.insertChannel(submat)
//
//        Core.inRange(frameHSV,
//                lower,
//                upper,
//                thresh)
//        val actual = if (color) submat else thresh
//        for (i in 1 until 4) actual.drawLine(y = (0.0..submat.height().toDouble()).lerp(i / 4.0), color = BLUE, thickness = 1)
//        return actual
//    }

    var fourbyone = Size(1.0, 4.0)
    override fun processFrame(input: Mat): Mat {
        val submat = input.submat(y, y + height, x, x + width)
        Imgproc.cvtColor(submat, frameHSV, Imgproc.COLOR_RGB2HSV)
        frameHSV.resize(fourbyone, small)

        var sum = 0
        repeat(4) {
            if (small.get(it, 0)[1] > 128.0) sum++
        }
        count = when (sum) {
            1, 2 -> 1
            3, 4 -> 4
            else -> 0
        }

        return submat
    }
}
class BasicHighGoalPipeline(target: Target) : OpenCvPipeline() {
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

    override fun processFrame(input: Mat): Mat {
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


}