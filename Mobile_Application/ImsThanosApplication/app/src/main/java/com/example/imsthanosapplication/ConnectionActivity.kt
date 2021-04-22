package com.example.imsthanosapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class ConnectionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)


        findViewById<Button>(R.id.connectBTN).setOnClickListener {
            object : CountDownTimer(7000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }
                override fun onFinish() {
                    BTObject.progressDialog.dismiss()
                    findViewById<TextView>(R.id.errorTV).text="Please try again"
                }
            }.start()
            val connection = BluetoothHandler(this)
            connection.execute()

            if (BTObject.connected) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{

            }
        }
    }


}