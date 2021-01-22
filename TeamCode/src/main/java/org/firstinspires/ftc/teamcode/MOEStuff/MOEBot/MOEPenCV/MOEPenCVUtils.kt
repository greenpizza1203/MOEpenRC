package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.util.Log
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraRotation
import kotlin.math.roundToInt


fun Mat.toBitMap(): Bitmap? {

    var bmp: Bitmap? = null
//    val tmp = Mat(height(), width(), CvType.CV_8U, Scalar(4.0))
    try {
//        Imgproc.cvtColor(this, tmp, Imgproc.COLOR_GRAY2RGBA, 4)
        bmp = Bitmap.createBitmap(this.cols(), this.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(this, bmp)
    } catch (e: CvException) {
        Log.d("Exception", e.message ?: "")
    }
    return bmp
}

fun OpenCvCamera.startStreaming(res: Size, rotation: OpenCvCameraRotation) {
    startStreaming(res.width.roundToInt(), res.height.roundToInt(), rotation)
}

//fun Mat.drawText(x: Double, y: Double, text: String, fontSize: Double = 1.0, color: Scalar = MOEPenCVConstants.BLACK) {
//    Imgproc.putText(this, text, Point(x, y), 0, fontSize, color)
//
//}

fun Mat.resize(width: Double, height: Double): Mat = resize(Size(width, height))

fun Mat.resize(size: Size): Mat {
    val dest = Mat()
    Imgproc.resize(this, dest, size)
    return dest
}

fun Mat.drawRect(rect: Rect, color: Scalar, thickness: Int = 3) {
    Imgproc.rectangle(this, rect, color, thickness)
//    Imgproc.rectangle(input, redRect, RED, 3)

}

fun Mat.drawVerticalLine(x: Double, color: Scalar, thickness: Int = 3) {
    Imgproc.line(this, Point(x, 0.0), Point(x, this.height().toDouble()), color, thickness)

}

fun Mat.drawText(x: Double, y: Double, text: String, fontSize: Double = 2.0, color: Scalar) {
    Imgproc.putText(this, text, Point(x, y), 0, fontSize, color, (fontSize * 3).roundToInt())

}


fun MatOfPoint.boundingRect(): Rect {
    return Imgproc.boundingRect(this)
}

fun Rect.centerX() = x + width / 2.0

object Colors {
    val RED = Scalar(255.0, 0.0, 0.0)
    val BLUE = Scalar(0.0, 0.0, 255.0)
}