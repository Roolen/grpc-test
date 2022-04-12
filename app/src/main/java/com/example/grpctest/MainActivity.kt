package com.example.grpctest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grpctest.databinding.ActivityMainBinding
import com.squareup.wire.GrpcClient
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Protocol

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}