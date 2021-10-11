package com.example.loweschallenge.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loweschallenge.data.remote.WeatherManager
import com.example.loweschallenge.data.remote.responseDTO.WeatherResponseDTO
import com.example.loweschallenge.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.example.loweschallenge.BuildConfig.API_ID

class DataRepositoryImpl: DataRepository {

    private val _getWeatherData: MutableLiveData<Resource<WeatherResponseDTO>> by lazy {
        MutableLiveData<Resource<WeatherResponseDTO>>()
    }
    val getWeatherData: LiveData<Resource<WeatherResponseDTO>> get() = _getWeatherData

    private suspend fun getData(
        city: String,
        appid: String = API_ID,
        units: String = "imperial"
    ) = flow {
        emit(Resource.Loading)
        val response = WeatherManager.getData()
        val state = if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("No data found")
        } else Resource.Error("Error fetching data")
        emit(state)
    }

    override suspend fun getWeather(city: String)  =
        withContext(Dispatchers.IO) {
            getData(city, API_ID, "imperial").collect {
                _getWeatherData.value = it
            }
        }
}