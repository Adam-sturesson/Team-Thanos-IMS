package com.example.imsthanosapplication.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.imsthanosapplication.DatabaseHandler
import com.example.imsthanosapplication.R
import com.example.imsthanosapplication.Routes as Routes1

class MowerPath : Fragment(R.layout.fragment_mower_path) {

    val db = DatabaseHandler.db

    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mower_path, container, false)
        var routesListView = view.findViewById<ListView>(R.id.routesListView)
        var routeItems = ArrayList<Routes1>()
        var itemsAdapter: ArrayAdapter<Routes1>? = null
        db.collection("Routes").get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents) {
                        val id = document.id.toString()
                        val startTime = document.getTimestamp("startTime")
                        val stopTime = document.getTimestamp("stopTime")
                        routeItems.add(Routes1(id, startTime, stopTime))
                    }
                    itemsAdapter = ArrayAdapter<Routes1>(
                        view.context as Context,
                        R.layout.list_view_item_routes,
                        R.id.routeItem_textView,
                        routeItems
                    )
                    routesListView.setAdapter(itemsAdapter)
                }
            }
        return view
    }
}