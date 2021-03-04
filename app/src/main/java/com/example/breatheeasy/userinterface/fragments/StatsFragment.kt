package com.example.breatheeasy.userinterface.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.breatheeasy.R
import com.example.breatheeasy.userinterface.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment(R.layout.fragment_stats) {

    private val viewModel: StatisticsViewModel by viewModels()

}