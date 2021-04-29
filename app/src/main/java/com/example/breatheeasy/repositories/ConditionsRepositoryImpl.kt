package com.example.breatheeasy.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.breatheeasy.additional.ContantValues.AIR_QUALITY_INDEX_AFFIRM
import com.example.breatheeasy.additional.ContantValues.FORECAST_DAYS_COUNT
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.CurrentConditionsResponse
import com.example.breatheeasy.data.APIData.ConditionsDataSource
import com.example.breatheeasy.data.APIData.ForecastResponse.ForecastConditionsResponse
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditionsDAO
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditionsSpecified
import com.example.breatheeasy.data.databases.conditions.forecast.ForecastConditionsDAO
import com.example.breatheeasy.data.databases.conditions.forecast.SimpleForecastConditionsSpecified
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocationDAO
import com.example.breatheeasy.data.providers.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

class ConditionsRepositoryImpl (
        private val currentConditionsDAO: CurrentConditionsDAO,
        private val forecastConditionsDAO: ForecastConditionsDAO,
        private val conditionsLocationDAO: ConditionsLocationDAO,
        private val conditionsDataSource: ConditionsDataSource,
        private val locationProvider: LocationProvider
        ) : ConditionsRepository {

    init {
        conditionsDataSource.apply {
            downloadedCurrentConditions.observeForever { newCurrentConditions ->
                persistFetchedCurrentConditions(newCurrentConditions)
            }
            downloadedForecastConditions.observeForever { newForecastConditions ->
                persistFetchedForecastConditions(newForecastConditions)
            }
        }
    }

    override suspend fun getCurrentConditions(): LiveData<CurrentConditionsSpecified> {
        initConditionsData()
        return withContext(Dispatchers.IO){
            return@withContext currentConditionsDAO.getCurrentConditions()
        }
    }

    override suspend fun getForecastConditionsList(startDate: LocalDate): LiveData<out List<SimpleForecastConditionsSpecified>> {
        return withContext(Dispatchers.IO) {
            initConditionsData()
            return@withContext forecastConditionsDAO.getSimpleForecastConditions(startDate)
        }
    }

    override suspend fun getConditionsLocation(): LiveData<ConditionsLocation> {
        return withContext(Dispatchers.IO){
            return@withContext conditionsLocationDAO.getLocation()
        }
    }

    private fun persistFetchedCurrentConditions(fetchedConditions : CurrentConditionsResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentConditionsDAO.upsert(fetchedConditions.currentConditions)
            conditionsLocationDAO.upsert(fetchedConditions.location)
        }
    }

    private fun persistFetchedForecastConditions(fetchedConditions: ForecastConditionsResponse) {
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            forecastConditionsDAO.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureConditionsList = fetchedConditions.futureConditionEntries.forecastDay
            forecastConditionsDAO.insert(futureConditionsList)
            conditionsLocationDAO.upsert(fetchedConditions.location)
        }
    }

    //Initisalise first network call in order to provide first caching of data in local database
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initConditionsData(){
        val lastConditionsLocation = conditionsLocationDAO.getLocationNonLive()

        if(lastConditionsLocation == null || locationProvider.hasLocationChanged(lastConditionsLocation)){
            fetchCurrentConditions()
            fetchForecastConditions()
            return
        }

        if (isFetchCurrentNeeded(lastConditionsLocation.zonedDateTime))
            fetchCurrentConditions()

        if(isFetchForecastNeeded())
            fetchForecastConditions()

    }

    private suspend fun fetchCurrentConditions(){
        conditionsDataSource.fetchCurrentConditions(
            locationProvider.getPreferredLocationString(),
            AIR_QUALITY_INDEX_AFFIRM
        )
    }

    private suspend fun fetchForecastConditions() {
        conditionsDataSource.fetchFutureWeather(
                locationProvider.getPreferredLocationString(),
                AIR_QUALITY_INDEX_AFFIRM
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchTime : ZonedDateTime): Boolean {
        val thirtyMinsAgo = ZonedDateTime.now().minusMinutes(30)

        return lastFetchTime.isBefore(thirtyMinsAgo)
    }

    private fun isFetchForecastNeeded() : Boolean {
        val today = LocalDate.now()
        val forecastConditionsCount = forecastConditionsDAO.countForecastConditions(today)
        return forecastConditionsCount < FORECAST_DAYS_COUNT
    }
}