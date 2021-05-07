package com.example.imsthanosapplication

import com.google.firebase.firestore.FirebaseFirestore


object DatabaseHandler{
  val db = FirebaseFirestore.getInstance()
}