package com.example.imsthanosapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.imsthanosapplication.DatabaseHandler
import com.example.imsthanosapplication.R
import com.example.imsthanosapplication.data.Route

class MowerPath : Fragment(R.layout.fragment_mower_path) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mower_path, container, false)
        var routesListView = view.findViewById<ListView>(R.id.routes_listView)
        var routeItems = DatabaseHandler.getAllRoutes()

        var arrayAdapter = ArrayAdapter<Route>(
            view.context as Context,
            android.R.layout.simple_spinner_item,
            routeItems
        )
        routesListView.setAdapter(arrayAdapter)

        return view;
    }
}