package processing.server


//import java.net.InetAddress

//
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.PPPath
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import processing.core.PApplet
import processing.ppsimulator.MOEApp
import processing.ppsimulator.PColor
import processing.ppsimulator.PathFollower
import processing.ppsimulator.options
import java.net.DatagramPacket
import java.net.DatagramSocket


fun main() {
    PApplet.main(UDPTelemetry::class.java)
}

//
class UDPTelemetry : MOEApp() {
    private val server = UDPServer()
    private val follower = PathFollower()

    init {
        server.start()
    }

    override fun settings() {
        size(600, 400)
//        fullScreen(2)
        do {
            val x = 4
        } while (x < 3)
    }

    override fun start() {
        super.start()
//        surface.setLocation(1920, 0)
        this.graphics.setSize(1920, 941)
    }

    private val scale = 4.0

    override fun draw() {
        background(0)
        scale(scale.toFloat());
        fill(0)

        rect(100f, 90f, 40f, 10f)
        fill(PColor.red(255))
        noStroke()
        for (i in server.points.indices) {
            if (i >= server.points.size) return
            val point = server.points[i]
            circle(point.x, point.y, 10.0)
        }


    }

    /**
     * Is called when a key is pressed; controls most of the program controls.
     */
    override fun keyPressed() {
        when (key) {
            'r' -> server.path.points.clear()
            'i' -> server.path.injectPoints()
            's' -> server.path.smoothPoints()
            'n' -> if (server.path.points.isNotEmpty()) {
                follower.setup(
                        server.path.points.first()
                )
            }
//            '+' -> options.lookAheadDistance += lookaheadDistanceDelta
//            '-' -> options.lookAheadDistance -= lookaheadDistanceDelta

        }
    }

}

class UDPServer : Thread() {
    private val socket: DatagramSocket = DatagramSocket(64308)
    private var running = false
    val buf = ByteArray(42)
    val packet = DatagramPacket(buf, buf.size)
    var path = PPPath(mutableListOf(), options)

    val points get() = path.points

    override fun run() {

        running = true
        println(socket.localPort)

        while (running) {
            socket.receive(packet)
            val x = ((buf[1].toInt() and 0x0f) shl 8) or (buf[0].toInt() and 0xFF)
            val y = buf[2].toInt() shl 4 or ((buf[1].toInt() and 0xf0) shr 4)
            val point = PPPoint(x.toDouble(), y.toDouble())
            point /= 4.0
            println(point)
            points.add(point)

        }
        socket.close()
    }


}

