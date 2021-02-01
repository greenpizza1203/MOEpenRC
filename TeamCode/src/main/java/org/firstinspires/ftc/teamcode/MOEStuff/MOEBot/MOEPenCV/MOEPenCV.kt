package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import android.graphics.Bitmap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.constants.Ref
import org.firstinspires.ftc.teamcode.constants.Ref.hardwareMap
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.opencv.core.Size
import org.openftc.easyopencv.*

data class MOEPenCVConfig(
        var pipeline: MOEPipeline,
        var resolution: Size = Size(800.0, 448.0),
        var camera: String = "HighCam"
)

class MOEPenCV(val config: MOEPenCVConfig) {
    var webcam: OpenCvCamera
    val pipeline = config.pipeline

    init {
        webcam = getExternalCamera()
        webcam.setPipeline(pipeline)
        webcam.openCameraDeviceAsync { webcam.startStreaming(config.resolution, OpenCvCameraRotation.UPRIGHT) }
    }

    private fun getExternalCamera(): OpenCvCamera {
        val webcamName = hardwareMap.get(WebcamName::class.java, config.camera)
        return OpenCvCameraFactory.getInstance().createWebcam(webcamName, getCameraMonitorView())
    }

    private fun getCameraMonitorView() = Ref.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)


//    fun getBitmap(): Bitmap? {
//        pipeline.requestFrame()
//        while (moeOpMode.isActive() && pipeline.lastFrame == null) {
//        }
//        return pipeline.lastFrame!!.toBitMap()!!
//    }
}