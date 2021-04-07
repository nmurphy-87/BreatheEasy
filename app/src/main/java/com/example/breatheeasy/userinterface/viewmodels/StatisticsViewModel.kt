package com.example.breatheeasy.userinterface.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.breatheeasy.repositories.RunRepository

class StatisticsViewModel @ViewModelInject constructor(
    val runRepository: RunRepository
) : ViewModel() {

}