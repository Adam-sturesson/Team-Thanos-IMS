package com.example.imsthanosapplication

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter

        var m_isConnected: Boolean = false
        var m_address: String = "48:01:c5:4b:50:f5"
        //var m_address: String = "14:D1:9E:C3:E2:9F"

        // var btMessenger: BluetoothMessageThread? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ConnectToDevice(this).execute()

    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
       // private val context: Context = c

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("Hej", "Connecting")
           // m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    Log.d("Hej", device.toString())
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    if(m_bluetoothSocket != null) {
                        m_bluetoothSocket?.connect()
                        Log.d("Hej", "You are connected")
                    }
                }
            } catch (e: IOException) {
                connectSuccess = false
                Log.d("Hej", e.toString())
                e.printStackTrace()
            }
            return null
        }
    }
}