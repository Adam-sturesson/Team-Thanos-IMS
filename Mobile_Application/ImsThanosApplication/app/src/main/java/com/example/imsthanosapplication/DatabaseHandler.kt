package com.example.imsthanosapplication

import android.util.Log
import com.example.imsthanosapplication.data.Route
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


object DatabaseHandler{
    private val db = FirebaseFirestore.getInstance()

    fun getAllRoutes(): MutableList<Route> {
         var routes = mutableListOf<Route>()
        db.collection("Routes").get().
            addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents){
                        var route = Route(document.id, document.getTimestamp("startTime"), document.getTimestamp("StopTime"))
                        Log.d("hej", route.toString())
                        routes.add(route)
                    }
                }
            }
        return routes
    }
}