package com.example.imsthanosapplication.data

import com.google.firebase.Timestamp

class Route (
    var id: String,
    var startTime : Timestamp?,
    var stopTime : Timestamp?

){
    override fun toString(): String {
        return startTime?.toDate().toString()
    }
}