package com.example.imsthanosapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton


class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*  Handler().postDelayed({
         //   btMessenger = BluetoothMessageThread(m_bluetoothSocket!!)
            btMessenger!!.start()
        }, 3000)
        */
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



    }



}