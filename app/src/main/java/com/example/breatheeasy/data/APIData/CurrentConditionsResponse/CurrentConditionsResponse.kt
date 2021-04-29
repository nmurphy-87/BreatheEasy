package com.example.breatheeasy.data.APIData.CurrentConditionsResponse

import com.example.breatheeasy.data.databases.conditions.current.CurrentConditions
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation
import com.google.gson.annotations.SerializedName


data class CurrentConditionsResponse(

        //SerializedName annotation used to ensure the currentAPIEntry val corresponds to the 'current' class in the JSON data
        @SerializedName("current")
        val currentConditions: CurrentConditions,

        val location: ConditionsLocation
)