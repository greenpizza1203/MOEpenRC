package org.firstinspires.ftc.teamcode.autonomous.vision

import android.annotation.SuppressLint
import android.util.Log
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.resize
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvPipeline
import org.openftc.easyopencv.OpenCvWebcam


class MOEPipelineAssist(val hardwareMap: HardwareMap, pipeline: OpenCvPipeline) {
    var webcam: OpenCvWebcam

    init {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class.java, "RingCam"), cameraMonitorViewId)
        webcam.setPipeline(pipeline)

        webcam.openCameraDeviceAsync {
            webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT)
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
            if (small.get(it, 0)[0] < 40.0) sum++
        }
        count = when (sum) {
            1, 2 -> 1
            3, 4 -> 4
            else -> 0
        }

        return submat
    }
}