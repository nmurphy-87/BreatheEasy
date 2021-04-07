package com.example.breatheeasy.repositories

import androidx.lifecycle.LiveData
import com.example.breatheeasy.data.databases.conditions.CurrentConditionsSpecified

interface ConditionsRepository {

    suspend fun getCurrentConditions() : LiveData<CurrentConditionsSpecified>

}