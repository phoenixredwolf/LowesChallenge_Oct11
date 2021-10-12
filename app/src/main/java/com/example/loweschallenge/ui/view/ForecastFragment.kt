package com.example.loweschallenge.ui.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.loweschallenge.databinding.FragmentForecastBinding
import com.example.loweschallenge.ui.adapter.DataAdapter
import com.example.loweschallenge.ui.viewmodel.MainViewModel
import com.example.loweschallenge.utility.Resource





class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding: FragmentForecastBinding get() = _binding!!

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
            viewModel.weather.observe(viewLifecycleOwner){ it ->
                progressBar.isVisible = it is Resource.Loading
                when(it) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        rvForecast.adapter = DataAdapter(it.data.weather) {
                            requireContext().toast(it.weather[0].description)
                            requireContext().toast(it.main.temp.toString())
                        }
                    }
                }
            }
        }
    }

    private fun Context.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}