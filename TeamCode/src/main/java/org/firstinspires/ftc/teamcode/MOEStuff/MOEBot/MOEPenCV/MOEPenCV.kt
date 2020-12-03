package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import android.graphics.Bitmap
import android.util.Log
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.constants.Ref
import org.firstinspires.ftc.teamcode.constants.Ref.hardwareMap
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.firstinspires.ftc.teamcode.constants.Ref.telemetry
import org.firstinspires.ftc.teamcode.utilities.internal.addData
import org.openftc.easyopencv.*


class MOEPenCV(val config: MOEOpenCVConfig) {
    var webcam: OpenCvCamera
//    val pipeline = MOEPipeline(this)
   val pipeline =  config.pipeline
    init {
        webcam = if (config.useInternalCamera) getInternalCamera() else getExternalCamera()
        webcam.setPipeline(pipeline)
//        webcam.stopStreaming()
//        webcam.closeCameraDevice()
        try {
            webcam.openCameraDevice()
            webcam.startStreaming(config.resolution, OpenCvCameraRotation.UPRIGHT)
        } catch (e: OpenCvCameraException) {
            telemetry.addData(e.message.toString())
            telemetry.update()
            Thread.sleep(500)
            moeOpMode.iRequestOpModeStop()
        }
    }

    private fun getInternalCamera(): OpenCvCamera {
        return if (config.enablePreview) {
            val cameraMonitorViewId: Int = getCameraMonitorView()
            OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId)
        } else {
            OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK)
        }
    }

    private fun getExternalCamera(): OpenCvCamera {
        val webcamName = hardwareMap.get(WebcamName::class.java, "Webcam 1")
        val instance = OpenCvCameraFactory.getInstance()
        return if (config.enablePreview) instance.createWebcam(webcamName, getCameraMonitorView()) else instance.createWebcam(webcamName)
    }

    private fun getCameraMonitorView() =
            Ref.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)

    fun stop() {
        Log.e("isCalled", "opencv")
        webcam.stopStreaming()
        webcam.closeCameraDevice()
    }

    fun getBitmap(): Bitmap? {
        pipeline.requestFrame()
        while (moeOpMode.isActive() && pipeline.lastFrame == null) {
        }
        return pipeline.lastFrame!!.toBitMap()!!
    }
}