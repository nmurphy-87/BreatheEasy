package com.example.breatheeasy.userinterface.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.breatheeasy.repositories.ConditionsRepository
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
class ConditionsViewModelFactory(
    private val conditionsRepository: ConditionsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConditionsViewModel(conditionsRepository) as T
    }
}