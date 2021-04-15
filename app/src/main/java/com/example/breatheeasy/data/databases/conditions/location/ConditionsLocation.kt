package com.example.breatheeasy.data.databases.conditions.location


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.breatheeasy.additional.ContantValues.CONDITION_LOCATION_PK
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "conditions_location")
data class ConditionsLocation(
    val country: String,
    val lat: Double,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id")
    val tzId: String
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = CONDITION_LOCATION_PK

    val zonedDateTime : ZonedDateTime
    get() {
        val instant = Instant.ofEpochSecond(localtimeEpoch)
        val zoneID = ZoneId.of(tzId)
        return ZonedDateTime.ofInstant(instant, zoneID)
    }


}