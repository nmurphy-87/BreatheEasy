package com.example.breatheeasy.data.databases.run

import androidx.lifecycle.LiveData
import androidx.room.*

// Data Access Object to provide CRUD functionality on our 'Run' database

@Dao
interface RunDAO {

    // INSERT RUN INTO OUR DATABASE AND IF IT ALREADY EXISTS WE DELETE IT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    //QUERIES FOR LISTING RUNS IN OUR RUN FRAGMENT

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC")
    fun getAllRunsByDate(): LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY timeInMilliseconds DESC")
    fun getAllRunsByTimeInMilliseconds(): LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY caloriesBurned DESC")
    fun getAllRunsByCalories(): LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY meanSpeed DESC")
    fun getAllRunsByAverageSpeed(): LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY distanceInMeters DESC")
    fun getAllRunsByDistance(): LiveData<List<Run>>

    //QUERIES FOR FUTURE STATISTICS FRAGMENT

    @Query("SELECT SUM(timeInMilliseconds) FROM run_table")
    fun getTotalTimeInMilliseconds(): LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM run_table")
    fun getTotalCalories(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM run_table")
    fun getTotalDistanceInMeters(): LiveData<Int>

    @Query("SELECT AVG(meanSpeed) FROM run_table")
    fun getTotalAverageSpeed(): LiveData<Long>

}