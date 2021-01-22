package processing.ppsimulator
//
//import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
//
//internal class PathFollower() {
//
//    // follower coordinates
//    lateinit var position: PPPoint
//    private val followerSpeed = 2.5
//
//    // follower speed
//    private val pointSize = 4.0
//    private val followerStopDistance = 2.0
//    var visible = false
//
//    // list of the points of the path drawn by the follower
//    private var followerPath = mutableListOf<PPPoint>()
//
//    /**
//     * Moves the follower towards a point by the follower's speed.
//     *
//     * @param target The value towards which to move.
//     */
//    private fun moveFollowerTowardsPoint(target: PPPoint) {
//        val offset = target - position
//        offset.normalize()
//        position += offset
//    }
//
//    var lastLookaheadIndex = 0
//    fun draw(app: PPSim) {
//        if (!this.visible) return
//        drawSelf(app)
//        drawPath(app)
//    }
//
//    private fun drawSelf(app: PPSim) {
//        //        val position = followerPosition
//        val (newIndex, lookahead) = app.path.findLookaheadPoint(position, lastLookaheadIndex)
//        lastLookaheadIndex = newIndex
//        // draw the follower
//        app.fill(255)
//        app.circle(position, pointSize)
//
//        // if lookahead exists
//        // draw the lookahead point
//        app.drawLookaheadPoint(position, lookahead)
//
//        // calculate the distance to the lookahead point
//        //        val deltaX = lookahead[0] - position[0]
//        //        val deltaY = lookahead[1] - position[1]
//
//        val distance = 2 * position.distanceTo(lookahead)
//
//        // draw the circle around the follower
//        app.noFill()
//        app.circle(position, distance)
//
//        // if the follower reached the destination, delete the follower
//
//        if (distance < followerStopDistance) {
//            reset()
//        } else {
//            // move the follower upon pressing 'f'
//            if (app.keyPressed && app.key == 'f') {
//                // add the follower's current position to its path
//                val position = position
//                followerPath.add(position)
//
//                // move it
//                moveFollowerTowardsPoint(lookahead)
//            }
//        }
//    }
//
//    private fun drawPath(app: PPSim) {
//        // skip some of the lines (increment by more than 1) to make the line seem dotted
////        if (followerPath.size < 2) return
//        for (i in followerPath.indices step 4) {
//            val p1 = followerPath[i]
//            val p2 = followerPath.getOrNull(i + 1) ?: return
//            app.stroke(0f,255f,0f)
//            app.strokeWeight(3f)
//            app.line(p1, p2)
//            app.strokeWeight(1f)
//        }
//    }
//
//    fun show() {
//        visible = true
//    }
//
//    fun reset() {
//        visible = false
//        lastLookaheadIndex = 0
//        followerPath.clear()
//
//    }
//
//    fun setup(point: PPPoint) {
//        this.position = PPPoint(point.x, point.y)
//        this.followerPath.clear()
//        show()
//    }
//
//
//}
//
