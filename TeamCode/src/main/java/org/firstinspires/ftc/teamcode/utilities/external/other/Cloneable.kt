package org.firstinspires.ftc.teamcode.utilities.external.other

interface Cloneable<out T> {
    fun clone(): T
}

//private fun <T : Cloneable> T.clone(): T {
//
//}