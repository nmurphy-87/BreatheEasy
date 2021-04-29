package com.example.breatheeasy.data.databases.conditions.forecast

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class SimpleForecastConditionsSpecified(

        @ColumnInfo(name = "date")
        val date: LocalDate,

        @ColumnInfo(name = "condition_icon")
        val conditionIconURL: String,

        @ColumnInfo(name = "avgtempC")
        val temperature: Double,

        )