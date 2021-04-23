package com.example.imsthanosapplication.ui.main
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.imsthanosapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.imsthanosapplication.ui.fragments.MowerContoller
import com.example.imsthanosapplication.ui.fragments.MowerPath


class MainActivity : AppCompatActivity() {

    //Suppresses warning for the buttons clicklisteners
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mowerControllerFragment=
            MowerContoller()
        val mowerPathFragment=
            MowerPath()

        setCurrentFragment(mowerControllerFragment)
        val navigation : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mowerController_item->setCurrentFragment(mowerControllerFragment)
                R.id.mowerPath_item->setCurrentFragment(mowerPathFragment)
            }
            true
        }

    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.active_fragment, fragment)
            commit()
        }
        /*  Handler().postDelayed({
         //   btMessenger = BluetoothMessageThread(m_bluetoothSocket!!)
            btMessenger!!.start()
        }, 3000)
        */
       /* val connection = BluetoothHandler(this)
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