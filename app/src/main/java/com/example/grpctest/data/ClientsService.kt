package com.example.grpctest.data

import com.example.grpctest.GreeterClient
import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ClientsService {
    private const val serverUrl = "http://10.0.2.2:5166"

    val GreeterClient by lazy {
        GrpcClient.Builder()
            .client(
                OkHttpClient.Builder().protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE))
                    .callTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    })
                    .build()
            )
            .baseUrl(serverUrl)
            .build()
            .create<GreeterClient>()
    }
}