package com.example.breatheeasy.data.databases.run

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

// A single Run entity (for saving each individual run) for our Room database

@Entity(tableName = "run_table")
data class Run(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var meanSpeed: Float = 0F,
    var distanceInMeters: Int = 0,
    var timeInMilliseconds: Long = 0L,
    var caloriesBurned: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}