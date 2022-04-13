package com.example.grpctest.view.fragments.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grpctest.GreeterClient
import com.example.grpctest.HelloRequest
import com.example.grpctest.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    val message = MutableLiveData<DataResult<String>>(Loading)

    fun loadMessage(client: GreeterClient) {
        message.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = client.SayHello().execute(HelloRequest("Artem"))
                message.postValue(Success(response.message))
            }
            catch (e: IOException) {
                val rpcException = e.toRpcException()
                message.postValue(Failed(rpcException.grpcStatus.code.toString()))
            }
        }
    }
}