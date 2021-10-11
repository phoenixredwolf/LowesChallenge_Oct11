package com.example.loweschallenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.loweschallenge.data.repo.DataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repo: DataRepositoryImpl) : ViewModel() {

    var city = ""

    val forecastData by lazy {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getWeather(city)
        }
    }

}
