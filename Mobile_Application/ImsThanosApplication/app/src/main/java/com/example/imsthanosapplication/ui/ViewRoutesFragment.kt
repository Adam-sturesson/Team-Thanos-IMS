package com.example.imsthanosapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.imsthanosapplication.data.DatabaseHandler
import com.example.imsthanosapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.imsthanosapplication.data.Route as Routes1

class ViewRoutesFragment : Fragment(R.layout.fragment_mower_path) {
    val TAG = "DocSnippets"
   // val db = DatabaseHandler.db

    var database = FirebaseDatabase.getInstance().reference
    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_mower_path, container, false)
        var routesListView = view.findViewById<ListView>(R.id.routesListView)
        var routeItems = ArrayList<Routes1>()
        var arrayAdapter: ArrayAdapter<Routes1>? = null

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG,"Exists")
                    val data = snapshot.child("Routes").children

                    data.forEach {
                        val id = it.key.toString()
                        //  val startTime = document.getTimestamp("startTime")
                        //  val stopTime = document.getTimestamp("stopTime")
                        routeItems.add(Routes1(id))
                    }
                    arrayAdapter = ArrayAdapter(
                        view.context as Context,
                        R.layout.list_view_item_routes,
                        R.id.routeItem_textView,
                        routeItems
                    )
                    routesListView.adapter = arrayAdapter
                    routesListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        val intent = Intent(view.context as Context,
                            CanvasActivity::class.java)
                        intent.putExtra("routeID", routeItems[position].id)
                        activity!!.startActivity(intent)
                    }
                }
                else{
                    Log.d(TAG, "snapshot does not exist")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG,"Canceld")
            }
        })

            /*


            db.collection("Routes").get()
                .addOnSuccessListener { documents ->
                    if (documents != null) {
                        for (document in documents) {
                            val id = document.id.toString()
                            val startTime = document.getTimestamp("startTime")
                            val stopTime = document.getTimestamp("stopTime")
                            routeItems.add(Routes1(id, startTime, stopTime))
                        }
                        arrayAdapter = ArrayAdapter<Routes1>(
                            view.context as Context,
                            R.layout.list_view_item_routes,
                            R.id.routeItem_textView,
                            routeItems
                        )
                        routesListView.setAdapter(arrayAdapter)
                        routesListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                            val intent = Intent(view.context as Context,
                                CanvasActivity::class.java)
                            intent.putExtra("routeID", routeItems[position].id)
                            this.startActivity(intent)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("error", "Error getting documents from database: ", exception)
                }*/
        return view
    }
}