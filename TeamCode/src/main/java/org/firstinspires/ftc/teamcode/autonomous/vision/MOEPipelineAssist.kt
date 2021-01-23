package org.firstinspires.ftc.teamcode.autonomous.vision

import android.annotation.SuppressLint
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPipeline
import org.opencv.core.Mat
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvPipeline
import org.openftc.easyopencv.OpenCvWebcam


class MOEPipelineAssist(val hardwareMap: HardwareMap, pipeline: OpenCvPipeline) {
    lateinit var webcam: OpenCvWebcam

    init {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName::class.java, "Webcam 1"), cameraMonitorViewId)
        webcam.setPipeline(pipeline)

        webcam.openCameraDeviceAsync {
            webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT)
        }
    }


}

class TestRingPipeline(val x: Int, val y: Int, val width: Int, val height: Int) : OpenCvPipeline() {
    @SuppressLint("SdCardPath")
    override fun init(mat: Mat) {
        saveMatToDisk(mat, "/sdcard/ftc2021/ring_${System.currentTimeMillis()}")
    }

    override fun processFrame(input: Mat): Mat {
        return input.submat(y, y + height, x, x + width)

    }

}