package com.example.breatheeasy.repositories

import androidx.lifecycle.LiveData
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditionsSpecified
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation

interface ConditionsRepository {

    suspend fun getCurrentConditions() : LiveData<CurrentConditionsSpecified>
    suspend fun getConditionsLocation(): LiveData<ConditionsLocation>

}