package com.example.loweschallenge.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loweschallenge.BuildConfig
import com.example.loweschallenge.data.model.Weather
import com.example.loweschallenge.data.model.WeatherDetails
import com.example.loweschallenge.data.remote.responseDTO.WeatherResponseDTO
import com.example.loweschallenge.data.repo.DataRepositoryImpl
import com.example.loweschallenge.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repo: DataRepositoryImpl) : ViewModel() {

    var city = ""
    var currWeather = ""
    var currTemp = ""
    private val dataRepo = repo
    private val _weatherData = MutableLiveData<Resource<WeatherResponseDTO>>()
    private var getForecastJob: Job? = null
    val weather: LiveData<Resource<WeatherResponseDTO>> get() = _weatherData

    fun getForecast() {
        getForecastJob = viewModelScope.launch {
            dataRepo.getData(city, BuildConfig.API_ID, "imperial").collect {
                _weatherData.postValue(it)
            }

        }
    }
}