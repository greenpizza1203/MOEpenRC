package org.firstinspires.ftc.teamcode.autonomous.sideconfig

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.Direction.*
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Rectangle

object AutonSideConstants {
    val blue = AutonSideConfig(
            movements = listOf(
                    // 0 - move from wall to stones
                    MovementOptions(Backwards, 28.0),
                    //1 - move to align with skystone
                    MovementOptions(Vertical, 2.0, -2.0, -11.0),
                    //2 - strafe towards skystone
                    MovementOptions(Right, 4.5, 4.0, 3.5),
                    //3 - strafe away from skystone
                    MovementOptions(Left, 9.0, 9.0, 7.5),
                    //4 - move towards foundation
                    MovementOptions(Forward, 66.5, 80.0, 88.0),
                    //5 - strafe towards foundation
                    MovementOptions(Right, 8.5, 9.5, 9.0),
                    //6 - strafe away from foundation
                    MovementOptions(Left, 8.5, 9.5, 10.5),
                    //7 - move backwards to second skystone
                    MovementOptions(Backwards, 97.0, 109.0, 115.0),
                    //8- move right towards second stone
                    MovementOptions(Right, 7.0, 8.0, 9.0),
                    //9- move left after grabbing second stone
                    MovementOptions(Left, 4.5, 4.5, 9.0),
                    //10- move forwards towards foundation
                    MovementOptions(Forward, 108.0, 115.0, 125.0),
                    //11- strafe right towards foundation
                    MovementOptions(Right, 10.5, 10.5, 11.0),
                    //12- strafe left away from foundation
                    MovementOptions(Left, 8.0, 10.5, 10.0),
                    //13- move backwards to park
                    MovementOptions(Backwards, 60.0, 61.0, 61.0)
            ),
            cropRectangle = Rectangle(420, 237, 290, 40),
            negateStuff = false
    )
    val red = AutonSideConfig(
            movements = listOf(
                    // 0 - move from wall to stones
                    MovementOptions(Backwards, 25.0),
                    //1 - move to align with skystone
                    MovementOptions(Vertical, -7.0, -1.0, 4.5),
                    //2 - strafe towards skystone
                    MovementOptions(Left, 3.5, 4.5, 3.5),
                    //3 - strafe away from skystone
                    MovementOptions(Right, 7.5, 9.0, 8.0),
                    //4 - move towards foundation
                    MovementOptions(Forward, 92.0, 78.0, 71.5),
                    //5 - strafe towards foundation
                    MovementOptions(Left, 9.0, 9.5, 9.0),
                    //6 - strafe away from foundation
                    MovementOptions(Right, 9.0, 9.5, 9.0),
                    //7 - align with second skystone
                    MovementOptions(Backwards, 115.0, 101.0, 97.0),
                    //8- strafe towards second stone
                    MovementOptions(Left, 10.0, 10.0, 12.0),
                    //9- strafe away after from second stone
                    MovementOptions(Right, 9.0, 4.5, 8.5),
                    //10- move forwards towards foundation
                    MovementOptions(Forward, 122.5, 115.0, 108.0),
                    //11- strafe towards foundation
                    MovementOptions(Left, 11.0, 9.5, 9.5),
                    //12- strafe left away from foundation
                    MovementOptions(Right, 10.0, 10.5, 8.0),
                    //13- move backwards to park
                    MovementOptions(Backwards, 58.0, 56.0, 55.0)
            ),
            cropRectangle = Rectangle(35, 200, 280, 34),
            negateStuff = true
    )

//    val red = AutonSideConfig(
//            movements = listOf(
//                    // 0 - move from wall to stones
//                    MovementOptions(Backwards, 28.0),
//                    //1 - move to align with skystone
//                    MovementOptions(Vertical, 2.0, -4.0, -11.0),
//                    //2 - strafe towards skystone
//                    MovementOptions(Right, 4.5, 4.0, 3.5),
//                    //3 - strafe away from skystone
//                    MovementOptions(Left, 9.0, 9.0, 7.5),
//                    //4 - move towards foundation
//                    MovementOptions(Forward, 66.5, 80.0, 88.0),
//                    //5 - strafe towards foundation
//                    MovementOptions(Left, 8.5, 9.5, 9.0),
//                    //6 - strafe away from foundation
//                    MovementOptions(Right, 8.5, 9.5, 10.5),
//                    //7 - move backwards to second skystone
//                    MovementOptions(Backwards, 97.0, 109.0, 117.0),
//                    //8- move right towards second stone
//                    MovementOptions(Left, 7.0, 8.0, 9.0),
//                    //9- move left after grabbing second stone
//                    MovementOptions(Right, 4.5, 4.5, 9.0),
//                    //10- move forwards towards foundation
//                    MovementOptions(Forward, 108.0, 115.0, 125.0),
//                    //11- strafe right towards foundation
//                    MovementOptions(Left, 10.5, 10.5, 11.0),
//                    //12- strafe left away from foundation
//                    MovementOptions(Right, 8.0, 10.5, 10.0),
//                    //13- move backwards to park
//                    MovementOptions(Backwards, 63.0, 64.0, 64.0)
//            ),
//            cropRectangle = Rectangle(10, 200, 280, 34),
//            flipSkystoneIndex = true,
//            negateStuff=true
//    )
}