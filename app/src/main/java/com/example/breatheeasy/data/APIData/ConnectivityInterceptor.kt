package com.example.breatheeasy.data.APIData

import okhttp3.Interceptor


//To throw an exception in case the user does not currently have an Internet connection
interface ConnectivityInterceptor : Interceptor