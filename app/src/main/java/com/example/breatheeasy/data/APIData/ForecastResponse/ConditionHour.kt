package com.example.breatheeasy.data.APIData.ForecastResponse


import com.google.gson.annotations.SerializedName

data class ConditionHour(
    val code: Int,
    val icon: String,
    val text: String
)