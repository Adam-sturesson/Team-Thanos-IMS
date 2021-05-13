package com.example.imsthanosapplication
import android.app.Service
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*

object bleSingleton {
    val deviceAddress = "00:1B:10:66:46:83"
    val UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb"
    val UUID_WRITE_CHARACTERISTIC = "0000ffe3-0000-1000-8000-00805f9b34fb"
    var mContext: Context? = null
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mBLEClass: BleClass? = null
    var mCurrentDevice: BluetoothDevice? = null
    var leHandler: Handler? = null
    var mIsConnected = false
    var mGattService :BluetoothGattService? = null
    var mGatt : BluetoothGatt? = null
    var mBle : BluetoothLE? = null
}


class BluetoothLE : Service() {
    var binder = Localbinder()

    init {
        bleSingleton.mBle = this
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun setup(context: Context) {
        bleSingleton.mContext = context
        var bluetoothManager : BluetoothManager? = null
        if (bleSingleton.mContext != null) {
            bluetoothManager = bleSingleton.mContext!!.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        }
        if (bluetoothManager != null) {
            bleSingleton.mBluetoothAdapter = bluetoothManager.adapter
        }

        if (bleSingleton.mBluetoothAdapter != null) {
            bleSingleton.mBluetoothAdapter!!.enable()
        }
        if (bleSingleton.mContext != null) {
            Log.d("hejsan","context: " +  bleSingleton.mContext.toString())
            bleSingleton.mBLEClass = BleClass(bleSingleton.mContext!!)
            Log.d("hejsan","mBLE: " +  bleSingleton.mBLEClass.toString())
        }
        if (!bleSingleton.mBLEClass!!.initialize()) {
            Log.d("hejsan", "Unable to initialize Bluetooth")
        }

    }

    fun stop() {
        bleSingleton.mIsConnected = false
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun close() {
        bleSingleton.mBLEClass!!.close()
        bleSingleton.mIsConnected = false
        bleSingleton.mCurrentDevice = null
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun selectDevice() {
        var device: BluetoothDevice? = null
        if (bleSingleton.mBluetoothAdapter != null) {
            device = bleSingleton.mBluetoothAdapter!!.getRemoteDevice(bleSingleton.deviceAddress)
        }
        if (bleSingleton.leHandler != null) {
            val msg: Message = bleSingleton.leHandler!!.obtainMessage(10) // ????????????????????
            bleSingleton.leHandler!!.sendMessage(msg)
        }
        if (device != null) {
            val conn = bleSingleton.mBLEClass!!.connect(device!!.address)
            if (conn) {
                bleSingleton.mIsConnected = true
                bleSingleton.mCurrentDevice = device
            }
        }
    }

    fun isConnected(): Boolean {
        return bleSingleton.mIsConnected
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun sendCommand(command:String): Boolean {

        Log.d("hejsan","mBLE null? " + (bleSingleton.mBLEClass == null).toString())
        var result = bleSingleton.mBLEClass!!.writeCharacteristic(command)
        return result
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun characteristicForProperty(): BluetoothGattCharacteristic? {
        return BluetoothGattCharacteristic(UUID.fromString(bleSingleton.UUID_WRITE_CHARACTERISTIC),8,16)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class Localbinder : Binder() {
        fun getService() :BluetoothLE {
            return this@BluetoothLE
        }
    }
}