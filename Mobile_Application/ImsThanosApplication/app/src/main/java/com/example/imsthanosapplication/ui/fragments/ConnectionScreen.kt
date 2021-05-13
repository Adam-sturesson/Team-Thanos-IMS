package com.example.imsthanosapplication.ui.fragments

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.imsthanosapplication.*


/**
 * A simple [Fragment] subclass.
 * Use the [Connection_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConnectionScreen : Fragment(R.layout.fragment_connection_screen) {
    var ble : BluetoothLE? = BluetoothLE()
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
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
                view.findViewById<TextView>(R.id.errorTV).text = getString(R.string.tryAgain)
            }
        }
        view.findViewById<Button>(R.id.connectBTN).setOnClickListener {
            if (bleSingleton.mBle != null) {
                timer.start()
                bleSingleton.mBle!!.setup(requireContext())
                bleSingleton.mBle!!.selectDevice()
                Log.d("hejsan", "pressed button connect " + ble!!.isConnected().toString())
            }
        }
        return view
    }

    fun btChangeFragment(timer:CountDownTimer, targetFragment: Fragment) {
        if (ble!!.isConnected()) {
            timer.cancel()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.active_fragment, targetFragment )
                commit()
            }
        }
    }
}