package org.firstinspires.ftc.teamcode.utilities.external

import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.PointImpl
import org.firstinspires.ftc.teamcode.utilities.external.other.Cloneable
import kotlin.math.pow

fun Double.toFixed(digits: Int = 3): String {
    //for the sake of javascript
//    val multiplier = 10.0.pow(digits)
//    val temp = (this * multiplier).toInt()
//    return (temp.toDouble() / multiplier).toString()
    return "%.${digits}f".format(this)
}

fun IntRange.coerceIn(indices: IntRange): IntRange {
    return IntRange(start.coerceAtLeast(indices.first), last.coerceAtMost(indices.last))
}

fun <T> Iterable<PointImpl<T>>.toDoubleList(): List<Double> = flatMap { listOf(it.x, it.y) }

fun <T : Cloneable<T>> Iterable<T>.clone(): List<T> = map { it.clone() }

fun <T : Comparable<T>> Iterable<T>.minIndex() = withIndex().minBy { it.value }?.index
fun <T> List<T>.subList(range: IntRange) = subList(range.first, range.last + 1)
