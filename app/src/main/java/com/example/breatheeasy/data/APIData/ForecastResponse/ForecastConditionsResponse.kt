package com.example.breatheeasy.data.APIData.ForecastResponse


import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation
import com.google.gson.annotations.SerializedName

data class ForecastConditionsResponse(
        @SerializedName("forecast")
        val futureConditionEntries: ForecastDaysContainer,
        val location: ConditionsLocation
)