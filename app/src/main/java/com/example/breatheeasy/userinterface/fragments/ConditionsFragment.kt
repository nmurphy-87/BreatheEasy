package com.example.breatheeasy.userinterface.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.breatheeasy.R
import com.example.breatheeasy.additional.glide.GlideApp
import com.example.breatheeasy.data.APIData.WeatherAndAirQualityAPIService
import com.example.breatheeasy.userinterface.base.ScopedFragment
import com.example.breatheeasy.userinterface.viewmodels.ConditionsViewModel
import com.example.breatheeasy.userinterface.viewmodels.ConditionsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_conditions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.math.round

@AndroidEntryPoint
class ConditionsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: ConditionsViewModelFactory by instance()


    private lateinit var viewModel: ConditionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conditions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ConditionsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = GlobalScope.launch(Dispatchers.Main){

        val getConditions = viewModel.conditions.await()
        val getLocation = viewModel.conditionsLocation.await()

        getLocation.observe(viewLifecycleOwner, Observer { location ->
            if(location == null){
                return@Observer
            }
            updateLocation(location.name)
        })

        getConditions.observe(viewLifecycleOwner, Observer {
            if(it == null){
                Log.d("CONDITIONS", "Conditions is null")
                return@Observer
            }

            // Update location and time in banner
            grpLoading.visibility = View.GONE
            updateTime()

            // Update Air Quality metrics
            updateDefraRating(it.DEFRAIndexGB)
            updateCO(it.carbonMonoxide)
            updateSO2(it.sulphurDioxide)
            updateNO2(it.nitrogenDioxide)
            updateO3(it.ozone)
            updateSmallParticles(it.particles2_5)
            updateLargeParticles(it.particles10)

            //Update weather metrics
            updateConditions(it.conditionText)
            updateTemperature(it.temperature)
            updateWindSpeed(it.windSpeed)
            updatePrecipitation(it.precipitation)

            // Glide automatic updating of weather icon
            GlideApp.with(this@ConditionsFragment)
                    .load("https:${it.conditionIconURL}")
                    .into(ivWeatherConditions)
        })
    }

    private fun updateLocation(location : String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "BreatheEasy - $location"
    }

    private fun updateTime() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Now"
    }

    // AIR QUALITY UPDATE UI FUNCTIONS

    private fun updateDefraRating(rating : Int) {

        val ratingAsString = rating.toString()

        when(rating){
            in 1..3 -> {
                ivAirQualityIcon.setImageResource(R.drawable.ic_good)
                tvDefraRating.text = "$ratingAsString - Low"
            }
            in 4..6 -> {
                ivAirQualityIcon.setImageResource(R.drawable.ic_caution)
                tvDefraRating.text = "$ratingAsString - Moderate"
            }
            in 7..9 -> {
                ivAirQualityIcon.setImageResource(R.drawable.ic_warning)
                tvDefraRating.text = "$ratingAsString - High"
            }
            else -> {
                ivAirQualityIcon.setImageResource(R.drawable.ic_warning)
                tvDefraRating.text = "$ratingAsString - Very High"
            }
        }
    }

    private fun updateCO(CO : Double){
        tvCarbonMonoxide.text = "CO: %.2f (μg/m3)".format(CO)
    }

    private fun updateSO2(SO2 : Double){
        tvSulphurDioxide.text = "SO2: %.2f (μg/m3)".format(SO2)
    }

    private fun updateNO2(NO2 : Double){
        tvNitrogenDioxide.text = "NO2: %.2f (μg/m3)".format(NO2)
    }

    private fun updateO3(O3 : Double){

        tvOzone.text = "O3: %.2f (μg/m3)".format(O3)
    }

    private fun updateSmallParticles(pm2_5 : Double){
        tvSmallParticles.text = "PM 2.5: %.2f (μg/m3)".format(pm2_5)
    }

    private fun updateLargeParticles(pm10 : Double){
        tvLargeParticles.text = "PM 10: %.2f (μg/m3)".format(pm10)
    }

    // WEATHER UPDATE UI FUNCTIONS

    private fun updateConditions(conditions : String) {
        tvWeatherConditions.text = conditions
    }

    private fun updateTemperature(temperature : Double) {
        tvTemperature.text = "$temperature °C"
    }

    private fun updateWindSpeed(windSpeed : Double) {
        tvWindSpeed.text = "Wind Speed: $windSpeed mph"
    }

    private fun updatePrecipitation(precipitation : Double){
        tvPrecipitation.text = "Precipitation: $precipitation mm"
    }

}