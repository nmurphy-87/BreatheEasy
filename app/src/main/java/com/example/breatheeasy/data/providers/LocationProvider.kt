package com.example.breatheeasy.data.providers

import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation

interface LocationProvider {

    suspend fun hasLocationChanged(lastConditionsLocation : ConditionsLocation) : Boolean
    suspend fun getPreferredLocationString() : String
}