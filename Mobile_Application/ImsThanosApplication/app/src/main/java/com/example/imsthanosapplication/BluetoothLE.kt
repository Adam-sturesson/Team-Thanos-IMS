package com.example.imsthanosapplication
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*


class BluetoothLE : Service() {
    private val deviceAddress = "00:1B:10:66:46:83"
    private val UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb"
    private val UUID_WRITE_CHARACTERISTIC = "0000ffe3-0000-1000-8000-00805f9b34fb"
    private var mContext: Context? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBLE: BleClass? = null
    private var mCurrentDevice: BluetoothDevice? = null
    var leHandler: Handler? = null
    private var mIsConnected = false

    private var _instance: BluetoothLE? = null

    fun sharedManager(): BluetoothLE? {
        if (_instance == null) {
            _instance = BluetoothLE()
        }
        return _instance
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun setup(context: Context) {
        mContext = context
        var bluetoothManager : BluetoothManager? = null
        if (mContext != null) {
            bluetoothManager = mContext!!.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        }
        if (bluetoothManager != null) {
            mBluetoothAdapter = bluetoothManager.adapter
        }

        if (mBluetoothAdapter != null) {
            mBluetoothAdapter!!.enable()
        }
        if (mContext != null) {
            Log.d("hejsan", mContext.toString())
            mBLE = BleClass(mContext!!)
            Log.d("hejsan", mBLE.toString())
        }
        if (!mBLE!!.initialize()) {
            Log.d("hejsan", "Unable to initialize Bluetooth")
        }

    }

    fun stop() {
        mIsConnected = false
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun close() {
        mBLE!!.close()
        mIsConnected = false
        mCurrentDevice = null
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun selectDevice() {
        var device: BluetoothDevice? = null
        if (mBluetoothAdapter != null) {
            device = mBluetoothAdapter!!.getRemoteDevice(deviceAddress)
        }
        if (leHandler != null) {
            val msg: Message = leHandler!!.obtainMessage(10) // ????????????????????
            leHandler!!.sendMessage(msg)
        }
        if (device != null) {
            val conn = mBLE!!.connect(device!!.address)
            if (conn) {
                mIsConnected = true
                mCurrentDevice = device
            }
        }
    }

    fun isConnected(): Boolean {
        return mIsConnected
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun sendCommand(command:String): Boolean {
        val ch: BluetoothGattCharacteristic? = characteristicForProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)
        if (ch != null) {
            ch.setValue(command)
            Log.d("hejsan","mBLE null? " + (mBLE == null).toString())
            mBLE!!.writeCharacteristic(ch)
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun characteristicForProperty(property: Int): BluetoothGattCharacteristic? {
        return BluetoothGattCharacteristic(UUID.fromString(UUID_WRITE_CHARACTERISTIC),8,16)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}