package org.firstinspires.ftc.teamcode.test

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.ParcelUuid
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import java.lang.reflect.Constructor
import java.net.*
import java.util.*


@TeleOp
@Disabled
class UDPTest : LinearOpMode() {

    override fun runOpMode() {
//        val address = InetSocketAddress("192.168.43.1", 42070)
//        val socket = DatagramSocket(null)
//        socket.bind(null)
//        val buffer = ByteArray(3)
//        val response = DatagramPacket(buffer, buffer.size)
//
//        telemetry.addData("socket", socket.localAddress.toString() + "," + socket.localPort)
//        telemetry.update()
        val socket = createL2CAPBluetoothSocket()
        socket.connect()
        telemetry.addData("connection", "complete")
        telemetry.update()

        waitForStart()
//        var count = 0;
//        while (opModeIsActive()) {
//            socket.receive(response)
//            count++
//
//            val x = ((buffer[1].toInt() and 0x0f) shl 8) or (buffer[0].toInt() and 0xFF)
//            val y = buffer[2].toInt() shl 4 or ((buffer[1].toInt() and 0xf0) shr 4)
//
//            telemetry.addData("x", x)
//            telemetry.addData("y", y)
//            telemetry.addData("count", count)
//            telemetry.update()
//        }
    }

    private val TYPE_RFCOMM = 1
    private val TYPE_SCO = 2
    private val TYPE_L2CAP = 3

    /**
     * Create a BluetoothSocket using L2CAP protocol
     * Useful for HID Bluetooth devices
     * @param BluetoothDevice
     * @return BluetoothSocket
     */
    private fun createL2CAPBluetoothSocket(): BluetoothSocket {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val pairedDevices = mBluetoothAdapter.bondedDevices
        val type = TYPE_L2CAP // L2CAP protocol
        val fd = -1 // Create a new socket
        val auth = true // No authentication
        val encrypt = true // Not encrypted
        val port = 0x14 // port to use (useless if UUID is given)
        val device = pairedDevices.first()
//        val uuid = ParcelUuid(UUID.fromString("00001124-0000-1000-8000-00805f9b34fb")) // Bluetooth UUID service
        val constructor: Constructor<BluetoothSocket> = BluetoothSocket::class.java.getDeclaredConstructor(
                Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType, Boolean::class.javaPrimitiveType,
                BluetoothDevice::class.java, Int::class.javaPrimitiveType, ParcelUuid::class.java)
        constructor.isAccessible = true
//      return BluetoothSocket(BluetoothSocket.TYPE_L2CAP, -1, false, false, this, channel, null)
        return constructor.newInstance(type, fd, auth, encrypt, device, port, null)

    }

}

