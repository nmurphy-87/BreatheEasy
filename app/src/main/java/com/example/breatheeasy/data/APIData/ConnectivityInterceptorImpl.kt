package com.example.breatheeasy.data.APIData

import android.content.Context
import android.net.ConnectivityManager
import com.example.breatheeasy.additional.NoNetworkConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isUserOnline()) {
            throw NoNetworkConnectionException()
        }
        return chain.proceed(chain.request())
    }

    private fun isUserOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}