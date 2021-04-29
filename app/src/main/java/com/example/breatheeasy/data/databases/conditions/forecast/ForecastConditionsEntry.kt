package com.example.breatheeasy.data.databases.conditions.forecast


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.breatheeasy.data.APIData.ForecastResponse.Day
import com.example.breatheeasy.data.APIData.ForecastResponse.Hour

@Entity(tableName = "future_conditions", indices = [Index(value = ["date"], unique = true)])
data class ForecastConditionsEntry(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = 0,
        val date: String,
        @Embedded
        val day: Day,
)