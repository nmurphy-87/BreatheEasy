package com.example.breatheeasy.data.APIData.ForecastResponse


import com.google.gson.annotations.SerializedName

data class ConditionX(
    val code: Int,
    val icon: String,
    val text: String
)