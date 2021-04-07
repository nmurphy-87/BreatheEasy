package com.example.breatheeasy.data.APIData.APIResponse

import com.example.breatheeasy.data.databases.conditions.CurrentConditions
import com.google.gson.annotations.SerializedName


data class WeatherAndAirQualityAPIResponse(

    //SerializedName annotation used to ensure the currentAPIEntry val corresponds to the 'current' class in the JSON data
    @SerializedName("current")
    val currentConditions: CurrentConditions,

    val location: Location
)