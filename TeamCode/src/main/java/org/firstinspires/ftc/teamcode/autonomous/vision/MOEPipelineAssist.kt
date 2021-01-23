package org.firstinspires.ftc.teamcode.autonomous.vision

import android.annotation.SuppressLint
import android.util.Log
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.opencv.core.Mat
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
        saveMatToDisk(input, filename)
        val cropped = input.submat(y, y + height, x, x + width)
        saveMatToDisk(cropped, "${filename}_cropped")
        Log.e("file", "saved to $filename")
    }

    override fun processFrame(input: Mat): Mat {
        return input.submat(y, y + height, x, x + width)

    }

}