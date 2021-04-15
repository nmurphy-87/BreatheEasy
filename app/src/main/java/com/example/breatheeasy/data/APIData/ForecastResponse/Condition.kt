package com.example.breatheeasy.data.APIData.ForecastResponse


import com.google.gson.annotations.SerializedName

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)