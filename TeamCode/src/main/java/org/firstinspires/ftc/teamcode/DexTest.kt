package org.firstinspires.ftc.teamcode

import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode

@Autonomous
class DexTest : OpMode() {
    fun test() {
        Log.e("plz", "work")
    }

    private val code = 151
    override fun init() {
//        AppUtil.getInstance().showProgress(UILocation.BOTH, "progress", 0.5)
//        Client.lastSocket.getOutputStream().write(code)
    }

    override fun init_loop() {
        telemetry.addData("hello", "word $code")
    }

    var count = 0L
    var progress = 0L
    override fun loop() {
        count++
        val speed: Long = 300
        if (count / speed > progress) progress = count / speed
        //        AppUtil.getInstance().showProgress(UILocation.BOTH, "progress", progress / 100.0);
        telemetry.addData("count", progress)
    }
}