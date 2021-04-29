package com.example.breatheeasy.data.APIData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.breatheeasy.additional.ContantValues.FORECAST_DAYS_COUNT
import com.example.breatheeasy.additional.NoNetworkConnectionException
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.CurrentConditionsResponse
import com.example.breatheeasy.data.APIData.ForecastResponse.ForecastConditionsResponse
import com.example.breatheeasy.services.WeatherAndAirQualityAPIService

class ConditionsDataSourceImpl (
    private val weatherAndAirQualityAPIService: WeatherAndAirQualityAPIService
        ) : ConditionsDataSource {

    private val _downloadedCurrentConditions = MutableLiveData<CurrentConditionsResponse>()

    override val downloadedCurrentConditions: LiveData<CurrentConditionsResponse>
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

    private val _downloadedForecastConditions = MutableLiveData<ForecastConditionsResponse>()

    override val downloadedForecastConditions: LiveData<ForecastConditionsResponse>
        get() = _downloadedForecastConditions

    override suspend fun fetchFutureWeather(location: String, getAirQuality: String) {

        try {
            val fetchedForecastConditions = weatherAndAirQualityAPIService
                    .getConditionsForecast(location, FORECAST_DAYS_COUNT, getAirQuality)
                    .await()
            _downloadedForecastConditions.postValue(fetchedForecastConditions)
        } catch (e : NoNetworkConnectionException) {
            Log.e("Connectivity", "No internet connection")
        }
    }


}