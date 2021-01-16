import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {

    val num = BigInteger("2").pow(1000)
    val total = num.toString().sumBy { it - '0' }
    println(total)

}




