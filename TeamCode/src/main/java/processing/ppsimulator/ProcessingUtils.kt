package processing.ppsimulator
//
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.LineSegment
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import processing.core.PApplet
//
//
val PScale = 1f;

class PMouse(val pApplet: PApplet) {
    val position: PPPoint
        get() = PPPoint(pApplet.mouseX.toDouble() / PScale, pApplet.mouseY.toDouble() / PScale)

}
//
open class MOEApp : PApplet() {
    val mouse by lazy {
        PMouse(this)
    }

    fun line(lineSegment: LineSegment) = line(lineSegment.start, lineSegment.end)
    fun line(start: PPPoint, end: PPPoint) = line(start.x, start.y, end.x, end.y)
    fun line(x1: Double, x2: Double, y1: Double, y2: Double) =
            line(PScale * x1.toFloat(), PScale * x2.toFloat(), PScale * y1.toFloat(), PScale * y2.toFloat())

    fun circle(center: PPPoint, radius: Double) = circle(center.x, center.y, radius)
    fun circle(x: Double, y: Double, radius: Double) = ellipse(x, y, radius, radius)
    fun ellipse(x: Double, y: Double, xRadius: Double, yRadius: Double) =
            ellipse(PScale * x.toFloat(), PScale * y.toFloat(), PScale * xRadius.toFloat(), PScale * yRadius.toFloat())

    fun fill(color: PColor) {
        fill(color.r.toFloat(), color.b.toFloat(), color.g.toFloat())
    }

    fun text(text: Any, x: Double, y: Double) {
        text(text.toString(),x.toFloat(),y.toFloat())
    }

}