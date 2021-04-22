package com.example.imsthanosapplication

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore




object DatabaseHandler{
    val db = FirebaseFirestore.getInstance()

    fun fetchPositions(){
        // Will fetch postions of mower in future
    }
}