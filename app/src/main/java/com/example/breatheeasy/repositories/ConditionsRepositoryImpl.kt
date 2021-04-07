package com.example.breatheeasy.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.breatheeasy.additional.ContantValues.AIR_QUALITY_INDEX_AFFIRM
import com.example.breatheeasy.data.APIData.APIResponse.WeatherAndAirQualityAPIResponse
import com.example.breatheeasy.data.APIData.ConditionsDataSource
import com.example.breatheeasy.data.databases.conditions.CurrentConditionsDAO
import com.example.breatheeasy.data.databases.conditions.CurrentConditionsSpecified
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class ConditionsRepositoryImpl (
    private val currentConditionsDAO: CurrentConditionsDAO,
    private val conditionsDataSource: ConditionsDataSource
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

    private fun persistFetchedCurrentConditions(fetchedConditions : WeatherAndAirQualityAPIResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentConditionsDAO.upsert(fetchedConditions.currentConditions)
        }
    }

    //Initisalise first network call in order to provide first caching of data in local database
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initConditionsData(){
        if (isFetchedCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchCurrentConditions()
        }
    }

    private suspend fun fetchCurrentConditions(){
        conditionsDataSource.fetchCurrentConditions(
            "Belfast",
            AIR_QUALITY_INDEX_AFFIRM
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchedCurrentNeeded(lastFetchTime : ZonedDateTime): Boolean {
        val thirtyMinsAgo = ZonedDateTime.now().minusMinutes(30)

        return lastFetchTime.isBefore(thirtyMinsAgo)
    }
}