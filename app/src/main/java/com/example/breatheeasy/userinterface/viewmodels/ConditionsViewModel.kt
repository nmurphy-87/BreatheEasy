package com.example.breatheeasy.userinterface.viewmodels

import androidx.lifecycle.ViewModel
import com.example.breatheeasy.additional.lazyDeferred
import com.example.breatheeasy.repositories.ConditionsRepository
import com.example.breatheeasy.userinterface.base.AbstractConditionsViewModel

class ConditionsViewModel(
    private val conditionsRepository: ConditionsRepository
) : AbstractConditionsViewModel(conditionsRepository) {

    val conditions by lazyDeferred {
        conditionsRepository.getCurrentConditions()
    }

}