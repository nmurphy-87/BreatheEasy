package com.example.breatheeasy.repositories

import androidx.lifecycle.LiveData
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditionsSpecified
import com.example.breatheeasy.data.databases.conditions.forecast.SimpleForecastConditionsSpecified
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation
import org.threeten.bp.LocalDate

interface ConditionsRepository {

    suspend fun getCurrentConditions() : LiveData<CurrentConditionsSpecified>
    suspend fun getConditionsLocation(): LiveData<ConditionsLocation>
    suspend fun getForecastConditionsList(startDate : LocalDate) : LiveData<out List<SimpleForecastConditionsSpecified>>

}