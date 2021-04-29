package com.example.breatheeasy.userinterface.base

import androidx.lifecycle.ViewModel
import com.example.breatheeasy.additional.lazyDeferred
import com.example.breatheeasy.repositories.ConditionsRepository

abstract class AbstractConditionsViewModel(
        private val conditionsRepository: ConditionsRepository
) : ViewModel() {

    val conditionsLocation by lazyDeferred {
        conditionsRepository.getConditionsLocation()
    }
}