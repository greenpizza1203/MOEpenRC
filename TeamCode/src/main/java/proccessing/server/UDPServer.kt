package proccessing.server


//import java.net.InetAddress

//
import org.firstinspires.ftc.teamcode.utilities.external.purepursuit.math.PPPoint
import proccessing.ppsimulator.MOEApp
import proccessing.ppsimulator.PColor
import processing.core.PApplet
import java.net.DatagramPacket
import java.net.DatagramSocket
import kotlin.random.Random


fun main() {
    PApplet.main(UDPTelemetry::class.java)
}

//
class UDPTelemetry : MOEApp() {
    private val server = UDPServer()

    init {
        server.start()
    }

    override fun settings() {
        size(600, 400   )
//        fullScreen(2)
    }

    override fun start() {
        super.start()
//        surface.setLocation(1920, 0)
        this.graphics.setSize(1920, 941)
    }

    override fun draw() {
        background(0)



        fill(0)

        rect(100f, 90f, 40f, 10f)
        fill(PColor.red(255))
//        val lastPoint = server.points.lastOrNull() ?: return
//        text(lastPoint.simpleString(), 100.0, 100.0)
        for (i in server.points.indices) {
            if (i >= server.points.size) return
            val point = server.points[i]
            circle(point.x, point.y, 10.0)
        }


    }


}

class UDPServer : Thread() {
    private val socket: DatagramSocket = DatagramSocket(54797)
    private var running = false
    val buf = ByteArray(42)
    val packet = DatagramPacket(buf, buf.size)

    var points = MutableList(30) { PPPoint(Random.nextInt(0, 1919).toDouble(), Random.nextInt(0, 941).toDouble()) }

    init {
//        points.add(PPPoint())
    }

    override fun run() {

        running = true
        println(socket.localPort)

        while (running) {
            socket.receive(packet)
            if (packet.length == 1) {
                points.clear()
            } else {
                val x = ((buf[1].toInt() and 0x0f) shl 8) or (buf[0].toInt() and 0xFF)
                val y = buf[2].toInt() shl 4 or ((buf[1].toInt() and 0xf0) shr 4)
                points.add(PPPoint(x.toDouble(), y.toDouble()))
            }

        }
        socket.close()
    }


}

