package com.example.breatheeasy.userinterface.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breatheeasy.data.databases.run.Run
import com.example.breatheeasy.repositories.RunRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    val runRepository: RunRepository
) : ViewModel() {

    val runsSortedByDate = runRepository.getAllRunsByDate()

    fun insertRun (run : Run) = viewModelScope.launch {
        runRepository.insertRun(run)
    }
}