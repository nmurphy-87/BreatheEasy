package com.example.breatheeasy

import android.app.Application
import android.content.Context
import com.example.breatheeasy.data.APIData.*
import com.example.breatheeasy.data.databases.conditions.ConditionsDatabase
import com.example.breatheeasy.data.providers.LocationProvider
import com.example.breatheeasy.data.providers.LocationProviderImpl
import com.example.breatheeasy.repositories.ConditionsRepository
import com.example.breatheeasy.repositories.ConditionsRepositoryImpl
import com.example.breatheeasy.userinterface.viewmodels.ConditionsViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

//Application class used as entry point for dependency injection

@HiltAndroidApp
class ApplicationClass : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ApplicationClass))

        //
        bind() from singleton { ConditionsDatabase(instance()) }
        bind() from singleton { instance<ConditionsDatabase>().getCurrentConditionsDAO()}
        bind() from singleton { instance<ConditionsDatabase>().getConditionsLocationDAO()}
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherAndAirQualityAPIService(instance()) }
        bind<ConditionsDataSource>() with singleton { ConditionsDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ConditionsRepository>() with singleton { ConditionsRepositoryImpl(instance(), instance(), instance(), instance()) }

        bind() from provider { ConditionsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}