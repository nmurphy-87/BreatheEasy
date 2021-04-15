package com.example.breatheeasy.data.databases.conditions.current

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.breatheeasy.additional.ContantValues.CURRENT_CONDITIONS_PK


@Dao
interface CurrentConditionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(currentConditions : CurrentConditions)

    @Query("SELECT * FROM current_conditions WHERE id = $CURRENT_CONDITIONS_PK")
    fun getCurrentConditions(): LiveData<CurrentConditionsSpecified>
}