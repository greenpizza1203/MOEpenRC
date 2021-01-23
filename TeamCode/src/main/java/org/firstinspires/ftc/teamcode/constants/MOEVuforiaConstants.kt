package org.firstinspires.ftc.teamcode.constants

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.teamcode.constants.Ref.hardwareMap

object MOEVuforiaConstants {
    val params = VuforiaLocalizer.Parameters().apply {
//        vuforiaLicenseKey = openRawResource(R.raw.vuforia_key).readLine()
        cameraName = hardwareMap.get(WebcamName::class.java, "HighCam")
    }
}

//const val LICENSE_KEY = "AVmJqSr/////AAABmeGcV96ftk3KgMDBC6fRLL4Qn/iAktqBNx29ewqo4NPP1TWeg6cpqfRV3dQ3vtDc4LxnLDckSBYdv9v3b1pe2Rq1v+stEVsKhckekqcdpbffWqz+QAyFJF7Mg9q/vOVBXIjvH7CPFVVKiM/M+J3vFw87SFxJKQlZuOM0WGi0hMJf8CE21ZJKh3h9tCg+/dqEux2FmB7XpezHFFeIqE8EK/3skt8Gjui+ywRSmgyzr+C3GswiIWsUn3YYCS6udgB8O6ntF5RZyrq4dQJxrdV1Mh1P7dlpGgyml+yiBAKDgoHZPiHKBx1TIY0Gg9QBebnuHdMvEOhK9oOJqtlR7XBO+fRJrXSCBY+9zBHRpZ6zSE0P"

//fun getVuforiaParameters(): VuforiaLocalizer.Parameters {
//    return VuforiaLocalizer.Parameters().apply {
//        vuforiaLicenseKey = MOEConstants.Vuforia.LICENSE_KEY
//        cameraName = ReferenceHolder.hardwareMap.get(WebcamName::class.java, "HighCam")
//    }
//}