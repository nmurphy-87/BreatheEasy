package com.example.breatheeasy.data.APIData

import androidx.lifecycle.LiveData
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.CurrentConditionsResponse
import com.example.breatheeasy.data.APIData.ForecastResponse.ForecastConditionsResponse

interface ConditionsDataSource {
    val downloadedCurrentConditions: LiveData<CurrentConditionsResponse>
    val downloadedForecastConditions: LiveData<ForecastConditionsResponse>

    suspend fun fetchCurrentConditions(
        location: String,
        getAirQuality: String
    )

    suspend fun fetchFutureWeather(
            location: String,
            getAirQuality: String
    )
}