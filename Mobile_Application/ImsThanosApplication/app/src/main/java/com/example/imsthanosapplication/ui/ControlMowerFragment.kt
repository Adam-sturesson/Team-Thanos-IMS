package com.example.imsthanosapplication.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import androidx.annotation.RequiresApi
import com.example.imsthanosapplication.BluetoothLE
import android.widget.TextView
import com.example.imsthanosapplication.R
import com.example.imsthanosapplication.bleSingleton
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class ControlMowerFragment : Fragment(R.layout.fragment_mower_contoller) {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor", "UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mower_contoller, container, false)

        val ble: BluetoothLE? = bleSingleton.mBle

        val materialButton: MaterialButtonToggleGroup = view.findViewById(R.id.materialButtons)
        materialButton.addOnButtonCheckedListener { _, checkedID, isChecked ->
            if (isChecked) {
                //the manual button is checked
                if (checkedID == R.id.manualDriving_button) {
                    ble?.sendCommand(getString(R.string.manualDriving))
                }
                //the autonomous button is checked
                else {
                    Log.d("hejsan" ,ble?.sendCommand(getString(R.string.autonomousDriving)).toString())
                }
            }
        }
        val startRouteSwitch = view.findViewById<Switch>(R.id.startRoute_switch)
        startRouteSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startRouteSwitch.text = resources.getString(R.string.stopRoute)
                ble?.sendCommand("p")
            } else {
                startRouteSwitch.text = resources.getString(R.string.startRoute)
                ble?.sendCommand("d")
            }
        }
        val forwardButton = view.findViewById<ImageButton>(R.id.forward_button)

        forwardButton.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    ble?.sendCommand(getString(R.string.forward))
                }
                MotionEvent.ACTION_UP -> {
                    ble?.sendCommand(getString(R.string.stop))
                }
            }
            v?.onTouchEvent(event) ?: true
        }
        val rightButton = view.findViewById<ImageButton>(R.id.right_button)
        rightButton.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    ble?.sendCommand(getString(R.string.right))
                }
                MotionEvent.ACTION_UP -> {
                    ble?.sendCommand(getString(R.string.stop))
                }
            }
            v?.onTouchEvent(event) ?: true
        }

        val leftButton = view.findViewById<ImageButton>(R.id.left_button)
        leftButton.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    ble?.sendCommand(getString(R.string.left))
                }
                MotionEvent.ACTION_UP -> {
                    ble?.sendCommand(getString(R.string.stop))
                }
            }
            v?.onTouchEvent(event) ?: true
        }

        val backwardButton = view.findViewById<ImageButton>(R.id.backward_button)
        backwardButton.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    ble?.sendCommand(getString(R.string.backward))
                }
                MotionEvent.ACTION_UP -> {
                    ble?.sendCommand(getString(R.string.stop))
                }
            }
            v?.onTouchEvent(event) ?: true
        }
        return view
    }
}