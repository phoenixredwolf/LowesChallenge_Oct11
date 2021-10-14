package com.example.loweschallenge.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loweschallenge.data.model.WeatherDetails
import com.example.loweschallenge.databinding.ForecastItemBinding

class DataAdapter(
    private val itemClicked: (WeatherDetails) -> Unit
) : RecyclerView.Adapter<DataAdapter.WeatherViewHolder>() {

    private var weatherDetails: List<WeatherDetails> = mutableListOf()


    //This method here is never called.
    // Should be called when you get a successful update
    // from your viewmodel
    fun updateWeatherList(weather: List<WeatherDetails>) {
        weatherDetails = weather
        Log.e("TAG", "updateWeatherList: Changing weather details", )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = WeatherViewHolder.inflate(parent).also { holder ->
        holder.itemView.setOnClickListener {
            itemClicked.invoke(weatherDetails[holder.adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int): Unit = with(holder) {
        Log.e("TAG", "onBindViewHolder: weatherDetails was $weatherDetails")
        bind(weatherDetails[position].weather[0].description, weatherDetails[position].main.temp.toString())
    }

    override fun getItemCount() = weatherDetails.size

    class WeatherViewHolder(
        private val binding: ForecastItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherDet: String, temp:String) = with(binding) {
            tvWeather.text = weatherDet
            tvTemp.text = temp

        }

        companion object {
            fun inflate(parent: ViewGroup) = WeatherViewHolder(
                ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}