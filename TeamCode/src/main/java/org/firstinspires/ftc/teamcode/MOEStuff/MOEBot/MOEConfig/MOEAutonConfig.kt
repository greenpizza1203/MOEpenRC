package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig

data class MOEAutonConfig(val isLeft: Boolean = true) {
}

interface MOEAutonConfigImpl {
    fun getAutonConfig() = MOEAutonConfig()
}