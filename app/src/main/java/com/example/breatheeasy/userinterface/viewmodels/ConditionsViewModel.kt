package com.example.breatheeasy.userinterface.viewmodels

import androidx.lifecycle.ViewModel
import com.example.breatheeasy.additional.lazyDeferred
import com.example.breatheeasy.repositories.ConditionsRepository

class ConditionsViewModel(
    private val conditionsRepository: ConditionsRepository
) : ViewModel() {

    val conditions by lazyDeferred {
        conditionsRepository.getCurrentConditions()
    }

    val conditionsLocation by lazyDeferred {
        conditionsRepository.getConditionsLocation()
    }

}