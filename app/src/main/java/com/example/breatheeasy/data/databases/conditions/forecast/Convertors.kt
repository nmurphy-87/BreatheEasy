package com.example.breatheeasy.data.databases.conditions.forecast

import androidx.room.TypeConverter
import com.example.breatheeasy.data.APIData.ForecastResponse.Hour
import com.google.gson.Gson
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class Convertors {

    @TypeConverter
    fun fromStringToDate(string: String?) = string?.let {
        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun fromDateToString(date: LocalDate) = date?.format(
            DateTimeFormatter.ISO_LOCAL_DATE
    )

    @TypeConverter
    fun fromListToJson(hour : List<Hour>?) = Gson().toJson(hour)

    @TypeConverter
    fun fromJsonToList(string: String) = Gson().fromJson(string, Array<Hour>::class.java)

}