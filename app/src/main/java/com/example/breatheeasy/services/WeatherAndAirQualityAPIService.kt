package com.example.breatheeasy.services

import com.example.breatheeasy.additional.ContantValues.AIR_QUALITY_INDEX_AFFIRM
import com.example.breatheeasy.additional.ContantValues.API_SERVICE_BASE_QUERY_URL
import com.example.breatheeasy.additional.ContantValues.API_SERVICE_KEY
import com.example.breatheeasy.data.APIData.ConnectivityInterceptor
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.CurrentConditionsResponse
import com.example.breatheeasy.data.APIData.ForecastResponse.ForecastConditionsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAndAirQualityAPIService {

    @GET("current.json")
    fun getCurrentWeatherAndAirQuality(
        @Query("q") location: String,
        @Query("aqi") getAirQuality: String = AIR_QUALITY_INDEX_AFFIRM
    ): Deferred<CurrentConditionsResponse>

    @GET("forecast.json")
    fun getConditionsForecast(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") getAirQuality: String = AIR_QUALITY_INDEX_AFFIRM
    ) : Deferred<ForecastConditionsResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherAndAirQualityAPIService {
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_SERVICE_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_SERVICE_BASE_QUERY_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAndAirQualityAPIService::class.java)
        }
    }
}