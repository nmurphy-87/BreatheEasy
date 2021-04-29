package com.example.breatheeasy.data.APIData.ForecastResponse


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Hour(
        @Embedded(prefix = "condition_")
    val condition: ConditionHour,
        @SerializedName("feelslike_c")
    val feelslikeC: Double,
        @SerializedName("gust_mph")
    val gustMph: Double,
        @SerializedName("heatindex_c")
    val heatindexC: Double,
        @SerializedName("heatindex_f")
    val heatindexF: Double,
        val humidity: Int,
        @SerializedName("is_day")
    val isDay: Int,
        @SerializedName("precip_mm")
    val precipMm: Double,
        @SerializedName("temp_c")
    val tempC: Double,
        @SerializedName("temp_f")
        val time: String,
        val uv: Double,
        @SerializedName("vis_miles")
    val visMiles: Double,
        @SerializedName("wind_mph")
    val windMph: Double,
)