package com.example.breatheeasy.userinterface.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breatheeasy.database.Run
import com.example.breatheeasy.repositories.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    val runsSortedByDate = mainRepository.getAllRunsByDate()

    fun insertRun (run : Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}