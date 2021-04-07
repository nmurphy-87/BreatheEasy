package com.example.breatheeasy.userinterface.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.breatheeasy.R
import com.example.breatheeasy.data.APIData.ConnectivityInterceptorImpl
import com.example.breatheeasy.data.APIData.WeatherAndAirQualityAPIService
import kotlinx.android.synthetic.main.fragment_landing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LandingFragment : Fragment(R.layout.fragment_landing) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvContinue.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_runFragment)
        }

    }
}