package com.example.grpctest.view.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.grpctest.data.ClientsService
import com.example.grpctest.databinding.FragmentMainBinding
import com.example.grpctest.utils.Success

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadMessage(ClientsService.GreeterClient)

        setObservers()
    }

    private fun setObservers() {
        viewModel.message.observe(viewLifecycleOwner) { result ->
            if (result is Success) {
                binding.message.text = result.data
            }
        }
    }
}