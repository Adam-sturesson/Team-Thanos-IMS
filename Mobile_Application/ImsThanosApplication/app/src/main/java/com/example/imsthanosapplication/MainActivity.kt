package com.example.imsthanosapplication
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        var m_address: String = "98:D3:81:FD:46:DA" //ändra till egna
        var btMessenger : BluetoothMessageThread? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Handler().postDelayed({
            btMessenger = BluetoothMessageThread(m_bluetoothSocket!!)
            btMessenger!!.start()
        }, 3000)

        ConnectToDevice(this).execute()




    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context = c

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")

            } else {
                m_isConnected = true
            }
            m_progress.dismiss()
        }

    }

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

                        message = message.trim('.')



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

    fun sendCommand(input: String) {
        val TAG = "BT_sendCommand"
        try {
            if (m_bluetoothSocket != null) {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            }
        }catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }

    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }


}