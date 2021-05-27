package com.example.imsthanosapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import com.example.imsthanosapplication.*



class ConnectToMowerFragment : Fragment(R.layout.fragment_connection_screen) {
    var ble : BluetoothLE? = BluetoothLE()
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_connection_screen, container, false)
        val targetFragment = ControlMowerFragment()


        val toggle = view.findViewById<ToggleButton>(R.id.connectBTN)

        toggle.text = getString(R.string.connect)
        toggle.textOff = getString(R.string.connect)
        toggle.textOn = getString(R.string.disconnect)

        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Connect to mower is clicked
                if (bleSingleton.mBle != null) {
                    bleSingleton.mBle!!.setup(requireContext())
                    bleSingleton.mBle!!.selectDevice()
                    if(ble!!.isConnected()){
                        Toast.makeText(view.context as Context, "connected",Toast.LENGTH_SHORT).show()
                    }
                    Log.d("hejsan", "pressed button connect " + ble!!.isConnected().toString())
                }
            } else {
                // the disconnect is clicked
                bleSingleton.mBLEClass?.close()
            }
        }

        return view
    }
}