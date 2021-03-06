package com.example.breatheeasy.repositories

import com.example.breatheeasy.data.databases.run.Run
import com.example.breatheeasy.data.databases.run.RunDAO
import javax.inject.Inject

class RunRepository @Inject constructor(
    val runDAO: RunDAO
) {

    suspend fun insertRun(run: Run) = runDAO.insertRun(run)
    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)

    fun getAllRunsByDate() = runDAO.getAllRunsByDate()
    fun getAllRunsByDistance() = runDAO.getAllRunsByDistance()
    fun getAllRunsByTimeInMilliseconds() = runDAO.getAllRunsByTimeInMilliseconds()
    fun getAllRunsByAverageSpeed() = runDAO.getAllRunsByAverageSpeed()
    fun getAllRunsByCalories() = runDAO.getAllRunsByCalories()

    fun getTotalDistance() = runDAO.getTotalDistanceInMeters()
    fun getTotalAverageSpeed() = runDAO.getTotalAverageSpeed()
    fun getTotalTime() = runDAO.getTotalTimeInMilliseconds()
    fun getTotalCalories() = runDAO.getTotalCalories()

}