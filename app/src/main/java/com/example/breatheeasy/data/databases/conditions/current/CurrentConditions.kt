package com.example.breatheeasy.data.databases.conditions.current


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.breatheeasy.additional.ContantValues.CURRENT_CONDITIONS_PK
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.AirQuality
import com.example.breatheeasy.data.APIData.CurrentConditionsResponse.Condition
import com.google.gson.annotations.SerializedName

@Entity(tableName = "current_conditions")
data class CurrentConditions(
    @SerializedName("air_quality")
    @Embedded(prefix = "airQuality_")
    val airQuality: AirQuality,
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    val uv: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double
) {
    // 'Constant' PK as we only intend to have one instance of 'Current Weather' in database
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_CONDITIONS_PK
}