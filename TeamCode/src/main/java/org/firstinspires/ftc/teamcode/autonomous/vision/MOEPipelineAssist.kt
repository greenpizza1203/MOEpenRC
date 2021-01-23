package org.firstinspires.ftc.teamcode.autonomous.vision

import android.annotation.SuppressLint
import android.util.Log
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvPipeline
import org.openftc.easyopencv.OpenCvWebcam


class MOEPipelineAssist(val hardwareMap: HardwareMap, pipeline: OpenCvPipeline) {
    lateinit var webcam: OpenCvWebcam

    init {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class.java, "RingCam"), cameraMonitorViewId)
        webcam.setPipeline(pipeline)

        webcam.openCameraDeviceAsync {

            webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT)
        }
    }


}

class TestRingPipeline(val x: Int, val y: Int, val width: Int, val height: Int) : OpenCvPipeline() {
    @SuppressLint("SdCardPath")
    override fun init(input: Mat) {
        val filename = "ring_${System.currentTimeMillis()}"
        val cropped = input.submat(y, y + height, x, x + width)
        saveMatToDisk(cropped, "${filename}_cropped")
        Log.e("file", "saved to $filename")
    }

    override fun processFrame(input: Mat): Mat {
        return input.submat(y, y + height, x, x + width)

    }

}

class BasicRingPipeline(val x: Int, val y: Int, val width: Int, val height: Int) : OpenCvPipeline() {
    val frameHSV = Mat()
    val small = Mat()
    val lowH = 0.0
    val highH = 180.0
    val thresh = Mat()

    @SuppressLint("SdCardPath")
    override fun init(input: Mat) {
        val filename = "ring_${System.currentTimeMillis()}"
//        val cropped = input.submat(y, y + height, x, x + width)
        saveMatToDisk(processFrame(input), "${filename}_processed")
        Log.e("file", "saved to $filename")
    }

    override fun processFrame(input: Mat): Mat {
        val submat = input.submat(y, y + height, x, x + width)
//        Imgproc.resize(submat, small, Size(1.0, 4.0))
        Imgproc.cvtColor(small, frameHSV, Imgproc.COLOR_RGB2HSV)
        Core.inRange(frameHSV,
                Scalar(lowH, 0.0, 0.0),
                Scalar(highH, 255.0, 255.0),
                thresh)
        return thresh

    }

}