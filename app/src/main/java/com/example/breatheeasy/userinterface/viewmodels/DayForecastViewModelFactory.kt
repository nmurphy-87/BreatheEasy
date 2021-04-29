package com.example.breatheeasy.userinterface.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.breatheeasy.repositories.ConditionsRepository

class DayForecastViewModelFactory(
        private val conditionsRepository: ConditionsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DayForecastViewModel(conditionsRepository) as T
    }
}