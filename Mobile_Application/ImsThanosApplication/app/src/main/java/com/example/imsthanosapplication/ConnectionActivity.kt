package com.example.imsthanosapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.imsthanosapplication.ui.main.MainActivity
import kotlin.concurrent.timer

class ConnectionActivity : AppCompatActivity() {
    var ble : BluetoothLE? = null
    var timer = object : CountDownTimer(15000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            btChangeActivity()
        }
        override fun onFinish() {
            findViewById<TextView>(R.id.errorTV).text="Please try again"
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)
        ble = BluetoothLE().sharedManager()

        findViewById<Button>(R.id.connectBTN).setOnClickListener {
            if (ble != null) {
                timer.start()
                ble!!.setup(this)
                ble!!.selectDevice()
                Log.d("hejsan", "om denna syns är adam dum " + ble!!.isConnected().toString())
            }

        }

    }

    fun btChangeActivity() {
        if (ble!!.isConnected()) {
            timer.cancel()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{

        }
    }
}