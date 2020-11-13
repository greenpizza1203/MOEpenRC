package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBotConstantsImpl
import org.firstinspires.ftc.teamcode.constants.Ref.robot
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.toNormalAngle


class MOEdometryGyro(val botConstants: MOEBotConstantsImpl) : MOEGyro() {


    override fun getRawEulerAngle(): Double = robot.odometry.getRawTheta()
    override fun initFinished(): Boolean {
        return true
    }

    override fun getRawAngle(): Double = getRawEulerAngle().toNormalAngle()
}

