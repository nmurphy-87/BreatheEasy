package com.example.breatheeasy.data.databases.conditions.location

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.breatheeasy.additional.ContantValues.CONDITION_LOCATION_PK


@Dao
interface ConditionsLocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(conditionsLocation: ConditionsLocation)

    @Query("SELECT * FROM conditions_location WHERE id = $CONDITION_LOCATION_PK")
    fun getLocation(): LiveData<ConditionsLocation>

    @Query("SELECT * FROM conditions_location WHERE id = $CONDITION_LOCATION_PK")
    fun getLocationNonLive(): ConditionsLocation?
}