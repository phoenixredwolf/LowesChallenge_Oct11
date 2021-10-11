package com.example.loweschallenge.data.remote.responseDTO

import com.example.loweschallenge.data.model.WeatherDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponseDTO(
    val cnt: Int,
    val cod: String,
    @Json(name="list")
    val weather: List<WeatherDetails>,
    val message: Int
)