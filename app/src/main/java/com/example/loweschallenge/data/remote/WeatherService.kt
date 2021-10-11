package com.example.loweschallenge.data.remote

import com.example.loweschallenge.BuildConfig
import com.example.loweschallenge.data.remote.responseDTO.WeatherResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    suspend fun getData(
        @Query("q") city: String,
        @Query("appid") apiKey: String = BuildConfig.API_ID,
        @Query("units") units: String = "imperial"
    ) : Response<WeatherResponseDTO>
}