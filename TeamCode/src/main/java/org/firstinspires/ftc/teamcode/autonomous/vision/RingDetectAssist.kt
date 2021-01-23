package org.firstinspires.ftc.teamcode.autonomous.vision

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPipeline
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines.MOERingPipeline
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvWebcam


class RingDetectAssist(hardwareMap: HardwareMap, pipeline: MOEPipeline) {
    lateinit var webcam: OpenCvWebcam

    init {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        webcam.setPipeline(pipeline)

        webcam.openCameraDeviceAsync {
            webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT)
        }
    }

}