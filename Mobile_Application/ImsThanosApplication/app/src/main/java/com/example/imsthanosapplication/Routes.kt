package com.example.imsthanosapplication

import com.google.firebase.Timestamp

class Routes (
    var id: String,
    var startTime : Timestamp?,
    var stopTime : Timestamp?

){
    override fun toString(): String {
        return startTime?.toDate().toString()
    }
}