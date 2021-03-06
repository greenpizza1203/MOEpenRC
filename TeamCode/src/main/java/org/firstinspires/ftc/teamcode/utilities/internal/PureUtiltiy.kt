package org.firstinspires.ftc.teamcode.utilities.internal

import android.graphics.Bitmap
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Rectangle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.reflect.KClass


fun Bitmap.crop(frame: Rectangle): Bitmap {
    return Bitmap.createBitmap(this, frame.x, frame.y, frame.width, frame.height)
}

fun Bitmap.saveTo(file: String) {
    saveTo(File(file))
}


fun Bitmap.saveTo(file: File) {
    try {
        FileOutputStream(file).use { out ->
            this.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

}


fun Bitmap.scale(width: Int, height: Int): Bitmap? =
        Bitmap.createScaledBitmap(this, width, height, false)

fun <T : Any> HardwareMap.get(clazz: KClass<T>, name: String) = get(clazz.java, name)!!
//operator fun DataSnapshot.get(s: String): Rectangle {
//
//}

