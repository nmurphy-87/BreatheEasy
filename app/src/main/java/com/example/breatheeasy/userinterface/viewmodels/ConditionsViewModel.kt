package com.example.breatheeasy.userinterface.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breatheeasy.additional.lazyDeferred
import com.example.breatheeasy.data.APIData.APIResponse.WeatherAndAirQualityAPIResponse
import com.example.breatheeasy.data.databases.conditions.CurrentConditionsSpecified
import com.example.breatheeasy.repositories.ConditionsRepository
import kotlinx.coroutines.launch

class ConditionsViewModel(
    private val conditionsRepository: ConditionsRepository
) : ViewModel() {

    val conditions by lazyDeferred {
        conditionsRepository.getCurrentConditions()
    }

    /*val conditions = MutableLiveData<CurrentConditionsSpecified>()

    init {
        viewModelScope.launch {
            conditions.value = conditionsRepository.getCurrentConditions()
        }
    }*/
}