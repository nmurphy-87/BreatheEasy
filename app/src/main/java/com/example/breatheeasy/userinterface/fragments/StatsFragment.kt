package com.example.breatheeasy.userinterface.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.breatheeasy.R
import com.example.breatheeasy.userinterface.viewmodels.StatisticsViewModel


class StatsFragment : Fragment(R.layout.fragment_stats) {

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}