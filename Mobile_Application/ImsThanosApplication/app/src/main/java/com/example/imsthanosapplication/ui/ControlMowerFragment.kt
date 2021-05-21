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
import androidx.annotation.RequiresApi
import com.example.imsthanosapplication.BluetoothLE
import android.widget.TextView

import android.widget.Switch

import com.example.imsthanosapplication.R
import com.example.imsthanosapplication.bleSingleton

class ControlMowerFragment : Fragment(R.layout.fragment_mower_contoller) {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mower_contoller, container, false)


        val ble : BluetoothLE? = bleSingleton.mBle

        val manualDrivingButton = view.findViewById<Button>(R.id.manualDriving_button)
        val autnomousDrivingButton = view.findViewById<Button>(R.id.autnomousDriving_button)
        manualDrivingButton.setOnClickListener {
            //Change color maybe in future
            if (ble != null) {
                ble.sendCommand(getString(R.string.manualDriving))
                manualDrivingButton.setBackgroundColor(Color.GREEN)
                autnomousDrivingButton.setBackgroundColor(Color.MAGENTA)
            }
        }

        autnomousDrivingButton.setOnClickListener {
            //Change color maybe in future
            if (ble != null) {
                ble.sendCommand(getString(R.string.autonomousDriving))
                autnomousDrivingButton.setBackgroundColor(Color.GREEN)
                manualDrivingButton.setBackgroundColor(Color.MAGENTA)
            }
        }

        val startRouteSwitch = view.findViewById<Switch>(R.id.startRoute_switch)
        startRouteSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                startRouteSwitch.text = resources.getString(R.string.stopRoute)
                Log.d("hejsan", "STARTED ROUTE")
                if (ble != null) {
                    var resault = ble.sendCommand("p")
                    Log.d("hejsan","Did it send:" + resault.toString())
                }
            }else{
                startRouteSwitch.text = resources.getString(R.string.startRoute)
                Log.d("hejsan", "STARTED ROUTE")
                if (ble != null) {
                    var resault = ble.sendCommand("d")
                    Log.d("hejsan","Did it send:" + resault.toString())
                }

            }
        }

        val forwardButton = view.findViewById<ImageButton>(R.id.forward_button)

        forwardButton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.forward))
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.stop))
                        }
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        val rightButton = view.findViewById<ImageButton>(R.id.right_button)
        rightButton.setOnTouchListener(object :
            View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.right))
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.stop))
                        }
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        val leftButton = view.findViewById<ImageButton>(R.id.left_button)
        leftButton.setOnTouchListener(object :
            View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.left))
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.stop))
                        }
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        val backwardButton = view.findViewById<ImageButton>(R.id.backward_button)
        backwardButton.setOnTouchListener(object :
            View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.backward))
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        if (ble != null) {
                            ble.sendCommand(getString(R.string.stop))
                        }
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        return view
    }
}