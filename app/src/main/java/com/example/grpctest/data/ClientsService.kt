package com.example.grpctest.data

import com.example.grpctest.GreeterClient
import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol

object ClientsService {
    private const val serverUrl = "http://10.0.2.2:5166"

    fun getGreeter(): GreeterClient {
        val grpcClient = GrpcClient.Builder()
            .client(
                OkHttpClient.Builder().protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE)).build()
            )
            .baseUrl(serverUrl)
            .build()
        return grpcClient.create()
    }
}