package com.example.breatheeasy.data.databases.conditions

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CurrentConditionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(currentConditions : CurrentConditions)

    @Query("SELECT * FROM current_conditions WHERE id = $CURRENT_CONDITIONS_PK")
    fun getCurrentConditions(): LiveData<CurrentConditionsSpecified>
}