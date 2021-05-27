package com.example.imsthanosapplication

import android.bluetooth.*
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*


class BleClass {

    private var mBluetoothManager: BluetoothManager? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothGatt: BluetoothGatt? = null

    interface OnConnectListener {
        fun onConnect(gatt: BluetoothGatt?)
    }


    interface OnDataAvailableListener {
        fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int)
        fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?)
    }

    private var mOnConnectListener: OnConnectListener? = null
    private var mOnDataAvailableListener: OnDataAvailableListener? = null
    private var mContext: Context? = null

    constructor (c: Context) {
        mContext = c
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    val bleGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mOnConnectListener?.onConnect(gatt)
                mBluetoothGatt?.discoverServices()
                bleSingleton.mGattService = mBluetoothGatt!!.getService(UUID.fromString(bleSingleton.UUID_KEY_DATA))
            }
        }

        override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        }
        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            bleSingleton.mGatt = gatt
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
        if (mBluetoothManager == null) {
            mBluetoothManager = mContext!!.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if (mBluetoothManager == null) {
                return false
            }
        }

        mBluetoothAdapter = mBluetoothManager!!.adapter
        if (mBluetoothAdapter == null) {
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun connect(address: String?): Boolean {
        if (mBluetoothAdapter == null || address == null) {
            return false
        }
        if (mBluetoothGatt != null) {
            return mBluetoothGatt!!.connect()
        }
        val device = mBluetoothAdapter!!.getRemoteDevice(address) ?: return false
        mBluetoothGatt = device.connectGatt(mContext, false, bleGattCallback)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun close() {
        if (mBluetoothGatt == null) {
            return
        }
        mBluetoothGatt!!.disconnect()
        mBluetoothGatt = null
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun writeCharacteristic(command: String): Boolean {
        if (bleSingleton.mGatt != null) {
            for (gattService in bleSingleton.mGatt!!.services) {
                for (characteristic in gattService.characteristics) {
                    if (characteristic.uuid.toString() == bleSingleton.UUID_WRITE_CHARACTERISTIC) {
                        characteristic.setValue(command)
                        return mBluetoothGatt!!.writeCharacteristic(characteristic)
                    }
                }
            }
        }
        return false
    }
}