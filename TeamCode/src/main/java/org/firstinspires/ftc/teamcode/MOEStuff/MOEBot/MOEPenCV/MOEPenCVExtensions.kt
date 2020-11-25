package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import android.graphics.Bitmap
import android.util.Log
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraRotation


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

fun OpenCvCamera.startStreaming(res: Resolution, rotation: OpenCvCameraRotation) {
    startStreaming(res.width, res.height, rotation)
}

fun Mat.drawText(x: Double, y: Double, text: String, fontSize: Double = 1.0, color: Scalar = MOEPenCVConstants.BLACK) {
    Imgproc.putText(this, text, Point(x, y), 0, fontSize, color)

}

fun Mat.resize(width: Double, height: Double): Mat = resize(Size(width, height))

fun Mat.resize(size: Size): Mat {
    val dest = Mat()
    Imgproc.resize(this, dest, size)
    return dest
}
