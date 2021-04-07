package com.example.breatheeasy.additional

import android.graphics.Color

object ContantValues {

    const val RUN_DATABASE_NAME = "run_db"
    const val CURRENT_CONDITIONS_DATABASE_NAME = "current_conditions.db"

    const val LOCATION_PERMISSION_REQUEST_CODE = 0

    // For Tracking Service Class
    const val ACTION_START_RESUME = "ACTION_START_RESUME"
    const val ACTION_STOP = "ACTION_STOP"
    const val ACTION_PAUSE = "ACTION_PAUSE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    // For Tracking Service Notification Channel
    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1

    //INTERVAL TIME FOR LOCATION TRACKING UPDATES - though this is not wholly accurate so we will set a minimum value for memory saving purposes
    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L
    const val TIMER_UPDATE_INTERVAL = 50L

    //POLYLINE CONSTANT APPEARANCE VARIABLES
    const val POLYLINE_COLOR = Color.GREEN
    const val POLYLINE_WIDTH = 10f
    const val MAP_ZOOM = 15f

    // WEATHER & AIR QUALITY API
    const val API_SERVICE_KEY = "39a4da9d4bb449b2840152921211202"
    const val API_SERVICE_BASE_QUERY_URL = "https://api.weatherapi.com/v1/"
    const val CURRENT_WEATHER_ID = 0
    const val AIR_QUALITY_INDEX_AFFIRM = "yes"


}