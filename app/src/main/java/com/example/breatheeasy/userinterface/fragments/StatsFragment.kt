package com.example.breatheeasy.userinterface.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.breatheeasy.R
import com.example.breatheeasy.additional.ContantValues.AIR_QUALITY_INDEX_AFFIRM
import com.example.breatheeasy.data.APIData.ConditionsDataSourceImpl
import com.example.breatheeasy.data.APIData.ConnectivityInterceptorImpl
import com.example.breatheeasy.data.APIData.WeatherAndAirQualityAPIService
import com.example.breatheeasy.userinterface.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class StatsFragment : Fragment(R.layout.fragment_stats) {

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}