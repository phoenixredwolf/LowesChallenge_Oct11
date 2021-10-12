package com.example.loweschallenge.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loweschallenge.data.remote.WeatherManager
import com.example.loweschallenge.data.remote.responseDTO.WeatherResponseDTO
import com.example.loweschallenge.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.example.loweschallenge.BuildConfig.API_ID

class DataRepositoryImpl {

    suspend fun getData(
        city: String,
        appid: String,
        units: String
    ) = flow {
        emit(Resource.Loading)
        val response = WeatherManager.getData(city, API_ID, "imperial")
        val state = if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("No data found")
        } else Resource.Error("Error fetching data")
        emit(state)
    }
}