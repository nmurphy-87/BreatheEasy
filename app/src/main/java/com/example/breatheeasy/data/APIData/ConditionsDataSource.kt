package com.example.breatheeasy.data.APIData

import androidx.lifecycle.LiveData
import com.example.breatheeasy.data.APIData.APIResponse.WeatherAndAirQualityAPIResponse

interface ConditionsDataSource {
    val downloadedCurrentConditions: LiveData<WeatherAndAirQualityAPIResponse>

    suspend fun fetchCurrentConditions(
        location: String,
        getAirQuality: String
    )
}