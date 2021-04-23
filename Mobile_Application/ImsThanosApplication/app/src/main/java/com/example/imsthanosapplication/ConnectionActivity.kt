package com.example.imsthanosapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.timer

class ConnectionActivity : AppCompatActivity() {
    var timer = object : CountDownTimer(15000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            btChangeActivity()
        }
        override fun onFinish() {
            BTObject.progressDialog.dismiss()
            findViewById<TextView>(R.id.errorTV).text="Please try again"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        findViewById<Button>(R.id.connectBTN).setOnClickListener {
          timer.start()
            val connection = BluetoothHandler(this)
            connection.execute()


        }

    }

    fun btChangeActivity() {
        if (BTObject.connected) {
            timer.cancel()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{

        }
    }



}