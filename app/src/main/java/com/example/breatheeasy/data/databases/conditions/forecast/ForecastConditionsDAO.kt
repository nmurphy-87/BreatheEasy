package com.example.breatheeasy.data.databases.conditions.forecast

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.threeten.bp.LocalDate

@Dao
interface ForecastConditionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastConditionsEntries: List<ForecastConditionsEntry>)

    @Query("SELECT * FROM future_conditions WHERE date(date) >= date(:startDate)")
    fun getSimpleForecastConditions(startDate : LocalDate) : LiveData<List<SimpleForecastConditionsSpecified>>

    @Query("SELECT * FROM future_conditions WHERE date(date) >= date(:startDate)")
    fun countForecastConditions(startDate: LocalDate): Int

    @Query("DELETE FROM future_conditions WHERE date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep : LocalDate)
}