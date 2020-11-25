package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import android.graphics.Bitmap
import com.vuforia.PIXEL_FORMAT
import com.vuforia.Vuforia
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOEVuforiaConfig
import org.firstinspires.ftc.teamcode.constants.MOEVuforiaConstants
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Rectangle
import org.firstinspires.ftc.teamcode.utilities.internal.crop


class MOEVuforia(val config: MOEVuforiaConfig) {
    var vuforia = getVuforiaLocalizaer()

    private fun getVuforiaLocalizaer(): VuforiaLocalizer? {
//vuforia.stop()
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGBA8888, true)
        if (config.enablePreview) {
            MOEVuforiaConstants.params.cameraMonitorViewIdParent = getMonitorViewId()
        }

        return ClassFactory.getInstance().createVuforia(MOEVuforiaConstants.params).apply {
            enableConvertFrameToBitmap()
            frameQueueCapacity = 1
        }
    }


    private fun getMonitorViewId(): Int {
        return 0
//        return R.id.cameraMonitorViewId

    }

    fun getCroppedBitmap(cropRect: Rectangle): Bitmap? {
        return getBitmap()?.crop(cropRect)
    }

    fun getBitmap(): Bitmap? {

        return vuforia?.let {
            it.convertFrameToBitmap(it.frameQueue.take())
        }

    }
}
