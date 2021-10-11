package com.example.loweschallenge.data.repo

interface DataRepository {

    suspend fun getWeather(city: String)
}