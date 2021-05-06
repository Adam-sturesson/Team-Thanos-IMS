package com.example.imsthanosapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.replace
import com.example.imsthanosapplication.BTObject
import com.example.imsthanosapplication.BluetoothHandler
import com.example.imsthanosapplication.R


/**
 * A simple [Fragment] subclass.
 * Use the [Connection_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConnectionScreen : Fragment(R.layout.fragment_connection_screen) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_connection_screen, container, false)
        val targetFragment = MowerContoller()
        var timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                btChangeFragment(this, targetFragment)
            }
            override fun onFinish() {
                BTObject.progressDialog.dismiss()
                view.findViewById<TextView>(R.id.errorTV).text = getString(R.string.tryAgain)
            }
        }
        view.findViewById<Button>(R.id.connectBTN).setOnClickListener {
            timer.start()
            val connection = BluetoothHandler( requireContext())
            connection.execute()
        }
        return view
    }

    fun btChangeFragment(timer:CountDownTimer, targetFragment: Fragment) {
        if (BTObject.connected) {
            timer.cancel()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.active_fragment, targetFragment )
                commit()
            }
        }
        else{

        }
    }
    companion object {

    }
}