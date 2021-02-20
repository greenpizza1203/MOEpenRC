package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.firstinspires.ftc.teamcode.constants.Ref.moeOpMode
import org.opencv.core.Mat
import org.openftc.easyopencv.OpenCvPipeline

open class MOEPipeline : OpenCvPipeline() {
    var lastFrame: Mat? = null
    var frameRequested = false

    init {
        MEMLEAK_DETECTION_ENABLED = false;
    }

    final override fun processFrame(input: Mat): Mat {
        if (frameRequested) {
            lastFrame = input
            frameRequested = false
        }
//        var processed = process(input)
        return if (!moeOpMode.isActive()) preview(input) else process(input)
//        val pre = preProcess(input)
    }

    open fun preview(input: Mat): Mat {
        return input
    }

    open fun process(input: Mat): Mat {
        return input
    }


    private fun processData(croppedMat: Mat): MutableList<Double> {
        val data = MutableList(4) {
            croppedMat.get(0, it)[2]
        }

        data[1] = (data[1] + data[2]) / 2.0
        data.removeAt(2)
        return data
    }


    fun requestFrame() {
        frameRequested = true
    }

    open fun savePicture(sync: Boolean = false) {
        val block: suspend CoroutineScope.() -> Unit = {
            requestFrame()
            while (moeOpMode.isActive() && lastFrame == null) {

            }
            if (lastFrame != null) saveMatToDisk(lastFrame, "${System.currentTimeMillis()}-teleop")
        }
        if (sync) runBlocking(block = block) else GlobalScope.launch(block = block)

    }


}