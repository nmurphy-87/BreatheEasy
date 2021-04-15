package com.example.breatheeasy.data.APIData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.breatheeasy.additional.NoNetworkConnectionException
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.WeatherAndAirQualityAPIResponse

class ConditionsDataSourceImpl (
    private val weatherAndAirQualityAPIService: WeatherAndAirQualityAPIService
        ) : ConditionsDataSource {

    private val _downloadedCurrentConditions = MutableLiveData<WeatherAndAirQualityAPIResponse>()

    override val downloadedCurrentConditions: LiveData<WeatherAndAirQualityAPIResponse>
        get() = _downloadedCurrentConditions

    override suspend fun fetchCurrentConditions(location: String, getAirQuality: String) {

        try {
            val fetchedCurrentConditions = weatherAndAirQualityAPIService
                .getCurrentWeatherAndAirQuality(location, getAirQuality)
                .await()
            _downloadedCurrentConditions.postValue(fetchedCurrentConditions)
        } catch (e : NoNetworkConnectionException) {
            Log.e("NetworkConnection", "No Internet Connection", e)
        }

    }
}