package com.example.roomdbwithviewmodel.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    val userName = MutableLiveData<String>()

    init {
        userName.value = "Asad"
    }
}