package com.example.imsthanosapplication

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class BTObject {
    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        //var m_address: String = "98:D3:81:FD:46:DA" //ändra till egna
        //var m_address: String = "14:D1:9E:C3:E2:9F"
        var m_address: String = "B8:27:EB:3A:D7:A4"
        //     var btMessenger : BluetoothMessageThread? = null
    }

}

class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {


    private var connectSuccess: Boolean = true
    private val context: Context = c

    override fun onPreExecute() {
        super.onPreExecute()
        Log.d("data", "Hello preExe")
        BTObject.m_progress = ProgressDialog.show(context, "Connecting...", "please wait")

    }

    override fun doInBackground(vararg p0: Void?): String? {
        try {
            if (BTObject.m_bluetoothSocket == null || !BTObject.m_isConnected) {
                BTObject.m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val device: BluetoothDevice = BTObject.m_bluetoothAdapter.getRemoteDevice(
                    BTObject.m_address
                )
                Log.d("data", device.toString())
                BTObject.m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(
                    BTObject.m_myUUID
                )
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                BTObject.m_bluetoothSocket!!.connect()
            }
        } catch (e: IOException) {
            Log.d("data","CATCH")
            connectSuccess = false
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (!connectSuccess) {
            Log.d("data", "couldn't connect")

        } else {
            BTObject.m_isConnected = true
            Log.d("data", "connected")
        }
        BTObject.m_progress.dismiss()

    }


    fun sendCommand(input: String) {
        val TAG = "data"
        try {
            if (BTObject.m_bluetoothSocket != null) {
                BTObject.m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            }
        }catch (e: IOException) {
            Log.d(TAG, "Could not close the connect socket", e)
        }
    }

    /*
    inner class BluetoothMessageThread(bluetoothSocket: BluetoothSocket) : Thread(){

        private val mmInStream: InputStream = m_bluetoothSocket?.inputStream!!
        private val mmOutStream: OutputStream = m_bluetoothSocket?.outputStream!!
        private var mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream
        private val TAG = "BluetoothThread"
        override fun run() {
            var numBytes: Int
            var message = ""

            while (true) {

                try {
                    // Read from the InputStream
                    numBytes = mmInStream.read(mmBuffer)
                    val readMessage = String(mmBuffer, 0, numBytes)
                    if (readMessage.contains(".")) {
                        message += readMessage

                        Log.d("num of bytes:", numBytes.toString())
                        Log.d("buffer:", mmBuffer.toString())

                        message = ""
                    } else {
                        message += readMessage
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "disconnected", e);

                }
            }
        }

        fun write(bytes: ByteArray) {
            try {
                mmOutStream.write(bytes)
            } catch (e: IOException) {
                Log.e(TAG, "Error occurred when sending data", e)
            }
        }



        fun cancel() {
            try {
                m_bluetoothSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the connect socket", e)
            }
        }
    }
*/
}