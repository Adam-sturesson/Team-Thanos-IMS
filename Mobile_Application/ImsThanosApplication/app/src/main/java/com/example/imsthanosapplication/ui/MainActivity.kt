package com.example.imsthanosapplication.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.imsthanosapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    //Suppresses warning for the buttons clicklisteners
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectionScreenFragment = ConnectToMowerFragment()
        val mowerControllerFragment = ControlMowerFragment()
        val mowerPathFragment = ViewRoutesFragment()

        setCurrentFragment(connectionScreenFragment)
        val navigation: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.connectScreen_item -> setCurrentFragment(connectionScreenFragment)
                R.id.mowerController_item -> setCurrentFragment(mowerControllerFragment)
                R.id.mowerPath_item -> setCurrentFragment(mowerPathFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.active_fragment, fragment)
        commit()
    }
}