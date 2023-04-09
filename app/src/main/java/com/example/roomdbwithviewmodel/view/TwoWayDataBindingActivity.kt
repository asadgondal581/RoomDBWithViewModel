package com.example.roomdbwithviewmodel.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.roomdbwithviewmodel.R
import com.example.roomdbwithviewmodel.databinding.ActivityTwoWayDataBindingBinding
import com.example.roomdbwithviewmodel.viewModel.MainActivityViewModel

class TwoWayDataBindingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTwoWayDataBindingBinding
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_two_way_data_binding)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}