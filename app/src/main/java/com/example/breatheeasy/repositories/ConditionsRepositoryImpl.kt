package com.example.breatheeasy.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.breatheeasy.additional.ContantValues.AIR_QUALITY_INDEX_AFFIRM
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.WeatherAndAirQualityAPIResponse
import com.example.breatheeasy.data.APIData.ConditionsDataSource
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditionsDAO
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditionsSpecified
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocationDAO
import com.example.breatheeasy.data.providers.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ConditionsRepositoryImpl (
        private val currentConditionsDAO: CurrentConditionsDAO,
        private val conditionsLocationDAO: ConditionsLocationDAO,
        private val conditionsDataSource: ConditionsDataSource,
        private val locationProvider: LocationProvider
        ) : ConditionsRepository {

    init {
        conditionsDataSource.downloadedCurrentConditions.observeForever { newCurrentConditions ->
            persistFetchedCurrentConditions(newCurrentConditions)
        }
    }

    override suspend fun getCurrentConditions(): LiveData<CurrentConditionsSpecified> {
        initConditionsData()
        return withContext(Dispatchers.IO){
            return@withContext currentConditionsDAO.getCurrentConditions()
        }
    }

    override suspend fun getConditionsLocation(): LiveData<ConditionsLocation> {
        return withContext(Dispatchers.IO){
            return@withContext conditionsLocationDAO.getLocation()
        }
    }

    private fun persistFetchedCurrentConditions(fetchedConditions : WeatherAndAirQualityAPIResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentConditionsDAO.upsert(fetchedConditions.currentConditions)
            conditionsLocationDAO.upsert(fetchedConditions.location)
        }
    }

    //Initisalise first network call in order to provide first caching of data in local database
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initConditionsData(){
        val lastConditionsLocation = conditionsLocationDAO.getLocation().value

        if(lastConditionsLocation == null || locationProvider.hasLocationChanged(lastConditionsLocation)){
            fetchCurrentConditions()
            return
        }

        if (isFetchCurrentNeeded(lastConditionsLocation.zonedDateTime))
            fetchCurrentConditions()

    }

    private suspend fun fetchCurrentConditions(){
        conditionsDataSource.fetchCurrentConditions(
            locationProvider.getPreferredLocationString(),
            AIR_QUALITY_INDEX_AFFIRM
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchTime : ZonedDateTime): Boolean {
        val thirtyMinsAgo = ZonedDateTime.now().minusMinutes(30)

        return lastFetchTime.isBefore(thirtyMinsAgo)
    }
}