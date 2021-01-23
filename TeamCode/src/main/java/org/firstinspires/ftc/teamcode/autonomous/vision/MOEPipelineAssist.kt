package org.firstinspires.ftc.teamcode.autonomous.vision

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPipeline
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvWebcam


class MOEPipelineAssist(hardwareMap: HardwareMap, pipeline: MOEPipeline) {
    lateinit var webcam: OpenCvWebcam

    init {
        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        webcam.setPipeline(pipeline)

        webcam.openCameraDeviceAsync {
            webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT)
        }
    }

}