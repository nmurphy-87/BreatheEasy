package com.example.breatheeasy.data.APIData.ForecastResponse

import com.example.breatheeasy.data.databases.conditions.forecast.ForecastConditionsEntry


data class ForecastDaysContainer(
    val forecastDay: List<ForecastConditionsEntry>
)