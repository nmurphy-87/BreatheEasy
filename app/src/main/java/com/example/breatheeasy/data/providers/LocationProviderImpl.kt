package com.example.breatheeasy.data.providers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.breatheeasy.additional.ContantValues
import com.example.breatheeasy.additional.ContantValues.DEFAULT_LOCATION_NAME
import com.example.breatheeasy.additional.ContantValues.DEVICE_LOCATION_KEY
import com.example.breatheeasy.additional.LocationPermissionNotGrantedException
import com.example.breatheeasy.additional.TrackingUtility
import com.example.breatheeasy.additional.TrackingUtility.hasLocationPermissions
import com.example.breatheeasy.additional.asDeferred
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import pub.devrel.easypermissions.EasyPermissions

class LocationProviderImpl(
        private val fusedLocationProviderClient: FusedLocationProviderClient,
        context: Context
) : PreferenceProvider(context), LocationProvider {

    private val activityContext = context.applicationContext

    override suspend fun hasLocationChanged(lastConditionsLocation: ConditionsLocation): Boolean {

        val locationChanged = try{
            hasOurLocationChanged(lastConditionsLocation)
        } catch (e : LocationPermissionNotGrantedException) {
            false
        }

        return locationChanged || hasDefaultLocationChanged(lastConditionsLocation)
    }

    override suspend fun getPreferredLocationString(): String {
        if(isUsingDeviceLocation()) {
            try{
                val deviceLocation = getLastDeviceLocation().await() ?: return "${getDefaulLocationName()}"
                return "${deviceLocation.latitude}, ${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return "${getDefaulLocationName()}"
            }
        } else {
            return "${getDefaulLocationName()}"
        }
    }

    private suspend fun hasOurLocationChanged(lastConditionsLocation: ConditionsLocation): Boolean {
        if(!isUsingDeviceLocation()) return false

        val deviceLocation = getLastDeviceLocation().await() ?: return false

        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - lastConditionsLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastConditionsLocation.lon) > comparisonThreshold
    }

    //ASKING SHARED PREFERENCES IF THE APPLICATION IS CURRENTLY USING DEVICE LOCATION
    private fun isUsingDeviceLocation() : Boolean{
        return preferences.getBoolean(DEVICE_LOCATION_KEY, true)
    }

    private fun getDefaulLocationName() : String? {
        return preferences.getString(DEFAULT_LOCATION_NAME, null)
    }

    //RETRIEVE THE LAST KNOWN DEVICE LOCATION
    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation() : Deferred<Location?> {
        return if (checkLocationPermissions())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun checkLocationPermissions(): Boolean {
        return TrackingUtility.hasLocationPermissions(activityContext)
    }

    private fun hasDefaultLocationChanged(lastConditionsLocation: ConditionsLocation) :Boolean {

        if(!isUsingDeviceLocation()) {
            val defaultLocationName = getDefaulLocationName()
            return defaultLocationName!= lastConditionsLocation.name
        }
        return false
    }


}