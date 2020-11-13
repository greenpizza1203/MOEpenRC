package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

class WrapperHandler(private val max: Double, val function: () -> Double) {

    var finalValue: Double = 0.0

    private var prev = finalValue
    private fun getDelta(curr: Double): Double {
        return prev.closestAngleDifference(curr)
    }

    fun getValue(): Double {

        val curr = function()
        val delta = getDelta(curr)
        finalValue += delta
        prev = curr
        return finalValue
    }

}

//fun main() {
//    val wrapperHandler = WrapperHandler(360.0) { readLine()?.toDouble()!! }
//    while (true) {
//        println(wrapperHandler.getValue())
//    }
//}