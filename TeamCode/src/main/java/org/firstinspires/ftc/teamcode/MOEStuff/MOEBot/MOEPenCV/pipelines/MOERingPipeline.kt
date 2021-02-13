package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.pipelines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.Colors.BLUE
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.MOEPipeline
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV.drawLine
import org.firstinspires.ftc.teamcode.constants.Ref
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.lerp
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.THRESH_TOZERO


class MOERingPipeline(val x: Int, val y: Int, val width: Int, val height: Int) : MOEPipeline() {
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


    override fun preview(input: Mat): Mat {
        val color = (System.currentTimeMillis() / 1000 % 20) < 10
        val submat = input.submat(y, y + height, x, x + width)
        //TODO: the stuff
        Imgproc.cvtColor(submat, frameHSV, Imgproc.COLOR_RGB2HSV)
//        Core.split(frameHSV, channels)
//        Core.extractChannel(frameHSV, hChannel, 0)
//        Imgproc.threshold(hChannel, hChannel, 40.0, 0.0, THRESH_TOZERO)
//        Core.insertChannel(submat)

        Core.inRange(frameHSV,
                lower,
                upper,
                thresh)
        val actual = if (color) submat else thresh
        for (i in 1 until 4) actual.drawLine(y = (0.0..submat.height().toDouble()).lerp(i / 4.0), color = BLUE, thickness = 1)
        return actual
    }

    var saved = false
    override fun process(input: Mat): Mat {
        if (!saved) {
            val submat = input.submat(y, y + height, x, x + width)
            saveMatToDisk(submat, "test")
            saved = true
        }
        Imgproc.cvtColor(input, frameHSV, Imgproc.COLOR_RGB2HSV)
        Core.inRange(frameHSV,
                lower,
                upper,
                thresh)
        return thresh
//        update(frame, thresh)
    }

    override fun savePicture(sync: Boolean) {
        val block: suspend CoroutineScope.() -> Unit = {
            requestFrame()
            while (Ref.moeOpMode.isActive() && lastFrame == null) {

            }
            if (lastFrame != null) saveMatToDisk(lastFrame, "${System.currentTimeMillis()}-teleop")
        }
        if (sync) runBlocking(block = block) else GlobalScope.launch(block = block)

    }
}