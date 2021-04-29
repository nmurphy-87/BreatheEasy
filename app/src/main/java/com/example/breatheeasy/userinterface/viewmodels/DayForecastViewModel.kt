package com.example.breatheeasy.userinterface.viewmodels

import androidx.lifecycle.ViewModel
import com.example.breatheeasy.additional.lazyDeferred
import com.example.breatheeasy.repositories.ConditionsRepository
import com.example.breatheeasy.userinterface.base.AbstractConditionsViewModel
import org.threeten.bp.LocalDate

class DayForecastViewModel(
        private val conditionsRepository: ConditionsRepository
) : AbstractConditionsViewModel(conditionsRepository) {

    val conditionEntries by lazyDeferred {
        conditionsRepository.getForecastConditionsList(LocalDate.now())
    }

}