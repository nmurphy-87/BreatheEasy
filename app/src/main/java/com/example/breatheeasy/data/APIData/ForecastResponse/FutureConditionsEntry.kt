package com.example.breatheeasy.data.APIData.ForecastResponse


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_conditions", indices = [Index(value = ["date"], unique = true)])
data class FutureConditionsEntry(
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    @Embedded
    val day: Day,
    val hour: List<Hour>
)