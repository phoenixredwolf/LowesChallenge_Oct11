package com.example.loweschallenge.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.loweschallenge.R
import com.example.loweschallenge.databinding.FragmentForecastBinding
import com.example.loweschallenge.ui.adapter.DataAdapter
import com.example.loweschallenge.ui.viewmodel.MainViewModel
import com.example.loweschallenge.utility.Resource





class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding: FragmentForecastBinding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    lateinit var dataAdapter: DataAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("TAG", "onCreateView: Making the Forecast fragment", )
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
            Log.i("Debug", "Instantiated viewmodel")
            dataAdapter = DataAdapter(){
                viewModel.getForecast()
                viewModel.currTemp = it.main.temp.toString()
                viewModel.currWeather = it.weather[0].description
                Log.i("Debug", "DataAdapter")
            }
            //As soon as the adapter is made, you can go ahead and assign it to
            //the RV because that part isnt going to change
            rvForecast.adapter = dataAdapter
            viewModel.weather.observe(viewLifecycleOwner){ it ->
                progressBar.isVisible = it is Resource.Loading
                when(it) {
                    is Resource.Loading -> {
                        progressBar.isVisible = true
                        Log.i("Debug", "Loading")
                    }
                    is Resource.Error -> {
                        Log.i("Debug", "Error")
                        Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        Log.i("Debug", "Success")
                        //This is where you wanna be updating dataAdapter's list
                        dataAdapter.updateWeatherList(it.data.weather)
                        //rvForecast.adapter = dataAdapter
                    }
                }
            }
            include.btnBack.setOnClickListener {
                view.findNavController().navigate(R.id.cityRequest)
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