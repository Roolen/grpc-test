package com.example.grpctest.view.fragments.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grpctest.GreeterClient
import com.example.grpctest.HelloRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val message = MutableLiveData("")

    fun loadMessage(client: GreeterClient) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = client.SayHello().execute(HelloRequest("Artem"))
                message.postValue(response.message)
            }
            catch (e: Exception) {}
        }
    }
}