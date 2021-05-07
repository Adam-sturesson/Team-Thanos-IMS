package com.example.imsthanosapplication.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.imsthanosapplication.BTObject
import android.widget.Switch
import com.example.imsthanosapplication.BluetoothHandler
import com.example.imsthanosapplication.R

class MowerContoller : Fragment(R.layout.fragment_mower_contoller) {
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
        if (!BTObject.connected) {
            view.findViewById<TextView>(R.id.connect_textView).text = getString(R.string.pleaseConnectToMower)
        }

        val connection = BluetoothHandler(requireActivity())
         //connection.execute()

        val manualDrivingButton = view.findViewById<Button>(R.id.manualDriving_button)
        val autnomousDrivingButton = view.findViewById<Button>(R.id.autnomousDriving_button)
        manualDrivingButton.setOnClickListener {
            //Change color maybe in future
            manualDrivingButton.setBackgroundColor(Color.GREEN)
            autnomousDrivingButton.setBackgroundColor(Color.MAGENTA)
            connection.sendCommand(getString(R.string.manualDriving))
        }


        autnomousDrivingButton.setOnClickListener {
            //Change color maybe in future
            autnomousDrivingButton.setBackgroundColor(Color.GREEN)
            manualDrivingButton.setBackgroundColor(Color.MAGENTA)
            Log.d("helpme", "auto")
            //connection.sendCommand(getString(R.string.autonomousDriving))
        }

        val startRouteSwitch = view.findViewById<Switch>(R.id.startRoute_switch)
        startRouteSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                startRouteSwitch.text = resources.getString(R.string.stopRoute)
                // write here the code send the data command
            }else{
                startRouteSwitch.text = resources.getString(R.string.startRoute)
                // write here the code send the data command

            }
        }

        val forwardButton = view.findViewById<ImageButton>(R.id.forward_button)

        forwardButton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.forward))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        val rightButton = view.findViewById<ImageButton>(R.id.right_button)
        rightButton.setOnTouchListener(object :
            View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.right))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        val leftButton = view.findViewById<ImageButton>(R.id.left_button)
        leftButton.setOnTouchListener(object :
            View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.left))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        val backwardButton = view.findViewById<ImageButton>(R.id.backward_button)
        backwardButton.setOnTouchListener(object :
            View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.backward))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        return view
    }
}