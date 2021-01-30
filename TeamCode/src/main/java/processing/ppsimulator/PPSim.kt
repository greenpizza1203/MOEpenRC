package processing.ppsimulator

import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.PPOptions
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.PPPath
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import processing.core.PApplet
import processing.core.PConstants

fun main() {
    PApplet.main(PPSim::class.java)
}

val options = PPOptions(
        overallMaxVelocity = 1.0,
        spacing = 3.0,
        smoothingTolerance = 0.01,
        smoothingA = 1 - 0.75,
        smoothingB = 0.75,
        turningConstant = 0.1,
        lookBack = 1,
        lookForward = 1,
        lookAheadDistance = 10.0,
        track_width = 28.0,
        K_V = 0.0,
        K_A = 0.0,
        K_P = 0.0
)

class PPSim : MOEApp() {
    var path = PPPath(mapOf(
            108.0 / 5 to 68.0 / 5,
            418.0 / 5 to 66.0 / 5,
            592.0 / 5 to 202.0 / 5,
            595.0 / 5 to 379.0 / 5,
            508.0 / 5 to 517.0 / 5,
            233.0 / 5 to 543.0 / 5,
            75.0 / 5 to 528.0 / 5
    ), options)

    init {
        path.points.forEach { println(it) }
    }

    // a PathFollower object and its variables
    internal var follower = PathFollower()

    // the value by which the stop distance changes
    private val lookaheadDistanceDelta = 2.5f

    // size of the points
    private val pointSize = 4.0

    // the lookahead distance
//    private var lookaheadDistance = 45.0f

    // the color of the pursued point
    private val pursuedCircleColor = PColor.red(255)

    override fun settings() {
        size(1600, 900)
    }

    override fun setup() {
        follower.reset()
        surface.setLocation(1920, 0)

    }

    /**
     * Resets the simulation by setting the follower and all of the paths to equal null.
     */
    private fun reset() {
        path = PPPath(mutableListOf(), options)
//        points = mutableListOf()
        follower.reset()
    }

    private val scale = 4.0
    override fun draw() {
        background(0)
        scale(scale.toFloat());


        // draw the path
        drawPath()

        drawLookahead()
        // draw and potentially move the PathFollower
        follower.draw(this)

    }
//
//    private fun updateFollower() {
//
//    }

    var lastMouseLookahead = 0
    private fun drawLookahead() {
        if (!mousePressed || mouseButton == PConstants.RIGHT) return
        noFill()
        stroke(120)
        circle(mouse.position / scale, options.lookAheadDistance * 2)

        val (newIndex, lookaheadPoint) = path.findLookaheadPoint(mouse.position / scale, lastMouseLookahead)
        lastMouseLookahead = newIndex
        drawLookaheadPoint(mouse.position / scale, lookaheadPoint)
    }

    private fun drawPath() {
        stroke(255)
        fill(255)
        path.points.zipWithNext { a, b ->
            line(a, b)
            // create an circle to symbolize a point
            circle(a, pointSize)
        }
        circle(path.points.last(), pointSize)

    }


    fun drawLookaheadPoint(start: PPPoint, end: PPPoint) {
        // line between object and lookahead point
        stroke(255)
        line(start, end)

        // fill the circle with the desired color of the point to be pursued
        fill(pursuedCircleColor)

        // lookahead point
        circle(end, pointSize)
    }

    /**
     * Is called when a key is pressed; controls most of the program controls.
     */
    override fun keyPressed() {
        when (key) {
            'r' -> reset()
            'i' -> path.injectPoints()
            's' -> path.smoothPoints()
            'n' -> if (path.points.isNotEmpty()) {
                follower.setup(
                        path.points.first()
                )
            }
            '+' -> options.lookAheadDistance += lookaheadDistanceDelta
            '-' -> options.lookAheadDistance -= lookaheadDistanceDelta

        }
    }

    /**
     * Is called when the mouse is pressed; creates new points.
     */
    override fun mousePressed() {
        // add a new path point upon pressing the right button
        if (mouseButton == RIGHT) {
            val list = path.points.toMutableList()
            list.add(mouse.position / scale)
//            println(mouseX, mouseY)
            path.points = list
        }
    }
}
