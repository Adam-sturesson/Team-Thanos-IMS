package com.example.imsthanosapplication

import android.R
import android.app.ProgressDialog
import android.app.Service
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.util.*
import java.util.Collections.list


class BTObject {


    companion object {
        var uuid: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")

        var bluetoothSocket: BluetoothSocket? = null
        private var mBluetoothGatt: BluetoothGatt? = null
        private var mBluetoothManager: BluetoothManager? = null
        lateinit var bluetoothAdapter: BluetoothAdapter

        var connected: Boolean = false
        //var m_address: String = "98:D3:81:FD:46:DA" //ändra till egna
        //var m_address: String = "14:D1:9E:C3:E2:9F"
        // var address: String = "B8:27:EB:3A:D7:A4"
        var address: String = "00:1B:10:66:46:83"
        //     var btMessenger : BluetoothMessageThread? = null
    }

}

class BluetoothHandler(c: Context) :   AsyncTask<Void, Void, String>()  {

    private var connectSuccess: Boolean = true
    private val context: Context = c


    @RequiresApi(Build.VERSION_CODES.R)

    val mLeScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d("hejsan","iausjd")
            var uuids : List<ParcelUuid> = result.scanRecord!!.serviceUuids
            Log.d("hejsan", uuids.toString())

        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onPreExecute() {
        super.onPreExecute()
        Log.d("hejsan", "Hello preExe")
        val scanner : BluetoothLeScanner = BTObject.bluetoothAdapter.bluetoothLeScanner

        scanner.startScan(mLeScanCallback)
    }


    override fun doInBackground(vararg p0: Void?): String? {
        try {
            if (BTObject.bluetoothSocket == null || !BTObject.connected) {
                BTObject.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


                /*
                private val mLeScanCallback = object : ScanCallback() {

    override fun onScanResult(callbackType: Int, result: ScanResult) {

        //scanned BLE processing device

        addDevice(result)

    }
 }
                val device: BluetoothDevice = BTObject.bluetoothAdapter.getRemoteDevice(
                    BTObject.address
                )
                Log.d("hejsan", device.toString())
                BTObject.bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(
                    BTObject.uuid
                )
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()*/
               // BTObject.bluetoothSocket!!.connect()
               connectSuccess = true
            }
        } catch (e: IOException) {
            Log.d("hejsan", e.message.toString())
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
            BTObject.connected = true
            Log.d("data", "connected")
        }
    }

    fun sendCommand(input: String) {
        val TAG = "data"
        try {
            if (BTObject.bluetoothSocket != null) {
                BTObject.bluetoothSocket!!.outputStream.write(input.toByteArray())
            }
        } catch (e: IOException) {
            Log.d(TAG, "Could not close the connect socket", e)
        }
    }

}