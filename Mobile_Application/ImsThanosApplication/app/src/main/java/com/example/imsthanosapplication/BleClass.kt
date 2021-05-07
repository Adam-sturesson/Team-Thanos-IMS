package com.example.imsthanosapplication

import android.bluetooth.*
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class BleClass {

    private var mBluetoothManager: BluetoothManager? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothGatt: BluetoothGatt? = null

    interface OnConnectListener {
        fun onConnect(gatt: BluetoothGatt?)
    }

    interface OnServiceDiscoverListener {
        fun onServiceDiscover(gatt: BluetoothGatt?)
    }

    interface OnDataAvailableListener {
        fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int)
        fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?)
    }

    private var mOnConnectListener: OnConnectListener? = null
    private var mOnServiceDiscoverListener: OnServiceDiscoverListener? = null
    private var mOnDataAvailableListener: OnDataAvailableListener? = null
    private var mContext: Context? = null

    fun setOnConnectListener(l: OnConnectListener) {
        mOnConnectListener = l
    }

    constructor (c: Context) {
        mContext = c
    }

    fun setOnServiceDiscoverListener(l: OnServiceDiscoverListener) {
        mOnServiceDiscoverListener = l
    }

    fun setOnDataAvailableListener(l: OnDataAvailableListener) {
        mOnDataAvailableListener = l
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    val bleGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mOnConnectListener?.onConnect(gatt)
                Log.d("hejsan", "Connected to Gattserver")
                Log.d("hejsan", "Attempting to start service discovery:" + mBluetoothGatt?.discoverServices())
            }
        }
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS && mOnServiceDiscoverListener != null) {
                mOnServiceDiscoverListener!!.onServiceDiscover(gatt)
            } else {
                Log.w("hejsan", "onServicesDiscovered received: $status")
            }
        }

        override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?,status: Int) {
            if (mOnDataAvailableListener != null) {
                mOnDataAvailableListener!!.onCharacteristicRead(gatt, characteristic, status)
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            if (mOnDataAvailableListener != null) {
                mOnDataAvailableListener!!.onCharacteristicWrite(gatt, characteristic)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun initialize(): Boolean {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = mContext!!.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if (mBluetoothManager == null) {
                Log.d("hejsan", "Unable to initialize BluetoothManager.")
                return false
            }
        }

        mBluetoothAdapter = mBluetoothManager!!.adapter
        if (mBluetoothAdapter == null) {
            Log.d("hejsan", "Unable to obtain a BluetoothAdapter.")
            return false
        }
        return true
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun connect(address: String?): Boolean {
        if (mBluetoothAdapter == null || address == null) {
            Log.d("hejsan", "BluetoothAdapter not initialized or unspecified address.")
            return false
        }
        Log.d("hejsan",mBluetoothGatt.toString())
        if (address != null && mBluetoothGatt != null) {
            Log.d("hejsan", "Trying to use an existing mBluetoothGatt for connection.")
            return if (mBluetoothGatt!!.connect()) {
                Log.d("hejsan", "Ble Connected.")
                true
            } else {
                Log.d("hejsan", "Ble Connecting fail.")
                false
            }
        }
        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        if (device == null) {
            Log.d("hejsan", "Device not found.  Unable to connect.")
            return false
        }
        mBluetoothGatt = device.connectGatt(mContext, false, bleGattCallback)
        Log.d("hejsan", "Trying to create a new connection.")
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun close() {
        if (mBluetoothGatt == null) {
            return
        }
        mBluetoothGatt!!.close()
        mBluetoothGatt = null
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun setCharacteristicNotification(characteristic: BluetoothGattCharacteristic?,
                                      enabled: Boolean) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w("hejsan", "BluetoothAdapter not initialized")
            return
        }
        mBluetoothGatt!!.setCharacteristicNotification(characteristic, enabled)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun writeCharacteristic(characteristic: BluetoothGattCharacteristic?) {
        Log.d("hejsan",characteristic?.value.toString())
        mBluetoothGatt!!.writeCharacteristic(characteristic)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun getSupportedGattServices(): List<BluetoothGattService?>? {
        return if (mBluetoothGatt == null) null else mBluetoothGatt!!.services as List<BluetoothGattService?>
    }
}