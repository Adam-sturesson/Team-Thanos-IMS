package com.example.imsthanosapplication.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imsthanosapplication.R

class mower_path : Fragment() {

    companion object {
        fun newInstance() = mower_path()
    }

    private lateinit var viewModel: MowerPathViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mower_path_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MowerPathViewModel::class.java)
        // TODO: Use the ViewModel
    }

}