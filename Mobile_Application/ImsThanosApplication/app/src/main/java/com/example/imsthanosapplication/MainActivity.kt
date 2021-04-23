package com.example.imsthanosapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var navView: BottomNavigationView

    //Suppresses warning for the buttons clicklisteners
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting the navView
        navView = findViewById(R.id.nav_view)

        //Disables back button in navbar fragments
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.nav_controller, R.id.nav_mower_path
                )
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)




        // Should be commented
        /*  Handler().postDelayed({
         //   btMessenger = BluetoothMessageThread(m_bluetoothSocket!!)
            btMessenger!!.start()
        }, 3000)
        */

        // Should not be commented
        /*
        val connection = BluetoothHandler(this)
        connection.execute()

        findViewById<Button>(R.id.manualDriving_button).setOnClickListener {
            //Change color maybe in future
            connection.sendCommand(getString(R.string.manualDriving))
        }

        findViewById<Button>(R.id.autnomousDriving_button).setOnClickListener {
            //Change color maybe in future
            connection.sendCommand(getString(R.string.autonomousDriving))
        }

        findViewById<ImageButton>(R.id.forward_button).setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.forward))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        findViewById<ImageButton>(R.id.right_button).setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action){
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.right))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        findViewById<ImageButton>(R.id.left_button).setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action){
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.left))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })

        findViewById<ImageButton>(R.id.backward_button).setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action){
                    MotionEvent.ACTION_DOWN -> connection.sendCommand(getString(R.string.backward))

                    MotionEvent.ACTION_UP -> connection.sendCommand(getString(R.string.stop))
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        */


    }

}