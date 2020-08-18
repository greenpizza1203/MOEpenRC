package org.firstinspires.ftc.teamcode;

import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

@Autonomous
public class DexTest extends OpMode {
    public void test() {
        Log.e("plz", "work");
    }

    private int code = 140;

    public void init() {
//        AppUtil.getInstance().showProgress(UILocation.BOTH, "progress", 0.5)
//        Client.lastSocket.getOutputStream().write(code)

    }

    public void init_loop() {
        telemetry.addData("hello", "word " + code);
    }

    long count = 0L;
    long progress = 0L;

    public void loop() {
        count++;
        long speed = 300;
        if (count / speed > progress) progress = count / speed;
//        AppUtil.getInstance().showProgress(UILocation.BOTH, "progress", progress / 100.0);
        telemetry.addData("count", progress);
    }
}