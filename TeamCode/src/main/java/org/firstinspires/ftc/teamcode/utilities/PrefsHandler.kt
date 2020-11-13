package org.firstinspires.ftc.teamcode.utilities

//
//object PrefsHandler {
//    private fun setFloat(pref: PrefKeys, value: Float) {
//        prefs?.edit()?.putFloat(pref.toString(), value)?.apply()
//    }
//
//    fun setDouble(pref: PrefKeys, value: Double) = setFloat(pref, value.toFloat())
//    fun getDouble(pref: PrefKeys, defaultValue: Double = 0.0) = getFloat(pref, defaultValue.toFloat())?.toDouble()
//    private fun getFloat(pref: PrefKeys, defaultValue: Float = 0f): Float? {
//        return prefs?.getFloat(pref.toString(), defaultValue)
//    }
//
//    val prefs by lazy {
//        activityRef?.getPreferences(Context.MODE_PRIVATE)
//    }
//
//}

enum class PrefKeys {
    AUTON_GYRO_FINAL_ANGLE
}