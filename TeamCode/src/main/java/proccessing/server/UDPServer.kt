package proccessing.server
//
//import proccessing.ppsimulator.MOEApp
//import processing.core.PApplet
//import java.net.DatagramPacket
//import java.net.DatagramSocket
//import java.net.InetAddress
//
//fun main() {
//    PApplet.main(UDPTelemetry::class.java)
//
//}
//
//class UDPTelemetry : MOEApp() {
//    private val server = UDPServer()
//
//    init {
//        server.start()
//    }
//    override fun settings() {
//        size(800, 600)
////        upda(1)
//    }
//
//    override fun draw() {
//        background(0)
//        text(server.buf.contentToString(), 100.0, 100.0)
//    }
//}
//
//
//class UDPServer : Thread() {
//    private val socket: DatagramSocket = DatagramSocket(4445)
//    private var running = false
//    val buf = ByteArray(10)
//    override fun run() {
//        running = true
//        while (running) {
//            var packet = DatagramPacket(buf, buf.size)
//            socket.receive(packet)
//            val address: InetAddress = packet.address
//            val port: Int = packet.port
//            packet = DatagramPacket(buf, buf.size, address, port)
////            String()
////            packet.
//            val received = String(packet.data, 0, packet.length)
//
//            if (received == "end") {
//                running = false
//                continue
//            }
//            socket.send(packet)
//        }
//        socket.close()
//    }
//
//}