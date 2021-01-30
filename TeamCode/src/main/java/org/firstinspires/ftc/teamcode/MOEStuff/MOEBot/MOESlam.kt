package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import com.arcrobotics.ftclib.geometry.Transform2d
import com.arcrobotics.ftclib.geometry.Translation2d
import com.spartronics4915.lib.T265Camera
import org.firstinspires.ftc.teamcode.constants.Ref.appContext
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.INCHES_IN_METER

//val globalSlam:MOE
class MOESlam {

    companion object {

        private val raw = T265Camera(Transform2d(), 0.0, appContext)

        init {
            Thread.sleep(1000)
            raw.start()
        }
    }

    fun pose(): Translation2d {
       return raw.lastReceivedCameraUpdate.pose.translation * INCHES_IN_METER
    }
//
//    init {
//
//    }

}