package com.example.breatheeasy.data.databases.conditions

import androidx.room.ColumnInfo


data class CurrentConditionsSpecified(

    @ColumnInfo(name = "airQuality_co")
    val carbonMonoxide: Double,
    @ColumnInfo(name = "airQuality_no2")
    val nitrogenDioxide: Double,
    @ColumnInfo(name = "airQuality_so2")
    val sulphurDioxide: Double,
    @ColumnInfo(name = "airQuality_o3")
    val ozone: Double,
    @ColumnInfo(name = "airQuality_pm25")
    val particles2_5: Double,
    @ColumnInfo(name = "airQuality_pm10")
    val particles10: Double,
    @ColumnInfo(name = "airQuality_gbDefraIndex")
    val DEFRAIndexGB: Int,

    @ColumnInfo(name = "condition_text")
    val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    val conditionIconURL: String,

    @ColumnInfo(name = "tempC")
    val temperature: Double,
    @ColumnInfo(name = "feelslikeC")
    val feelsLikeTemperature: Double,
    @ColumnInfo(name = "precipMm")
    val precipitation: Double,
    @ColumnInfo(name = "windMph")
    val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    val windDirection: String
)
