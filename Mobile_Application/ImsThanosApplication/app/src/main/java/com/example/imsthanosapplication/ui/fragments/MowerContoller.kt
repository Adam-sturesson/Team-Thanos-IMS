package com.example.imsthanosapplication.ui.fragments

import android.annotation.SuppressLint
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
import com.example.imsthanosapplication.BluetoothHandler
import com.example.imsthanosapplication.BluetoothLE
import com.example.imsthanosapplication.R

class MowerContoller : Fragment(R.layout.fragment_mower_contoller) {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mower_contoller, container, false)
        /*  Handler().postDelayed({
         //   btMessenger = BluetoothMessageThread(m_bluetoothSocket!!)
            btMessenger!!.start()
        }, 3000)
        */

        val ble : BluetoothLE? = BluetoothLE().sharedManager()
         //connection.execute()

        val manualDrivingButton = view.findViewById<Button>(R.id.manualDriving_button)
        manualDrivingButton.setOnClickListener {
            //Change color maybe in future
            if (ble != null) {
                ble.sendCommand(getString(R.string.manualDriving))
            }
        }

        val autnomousDrivingButton = view.findViewById<Button>(R.id.autnomousDriving_button)
        autnomousDrivingButton.setOnClickListener {
            //Change color maybe in future
            Log.d("helpme", "auto")
            //connection.sendCommand(getString(R.string.autonomousDriving))
        }

        val forwardButton = view.findViewById<ImageButton>(R.id.forward_button)

        forwardButton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (ble != null) {
                            Log.d("hejsan","forward good ? " + ble.sendCommand(getString(R.string.forward)).toString())
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