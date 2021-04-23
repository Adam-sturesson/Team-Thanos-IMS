package com.example.imsthanosapplication.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imsthanosapplication.R

class mower_controller : Fragment() {

    companion object {
        fun newInstance() = mower_controller()
    }

    private lateinit var viewModel: MowerControllerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mower_controller_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MowerControllerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}