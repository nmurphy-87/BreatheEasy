package com.example.breatheeasy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Application class to tell program to use Dagger Hilt for Dependency Injection

@HiltAndroidApp
class ApplicationClass : Application() {

}