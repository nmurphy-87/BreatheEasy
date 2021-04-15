package com.example.breatheeasy.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.breatheeasy.additional.ContantValues.RUN_DATABASE_NAME
import com.example.breatheeasy.data.databases.run.RunDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Create an instance of the Run Database
    @Singleton
    @Provides
    fun provideRunDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        RunDatabase::class.java,
        RUN_DATABASE_NAME
    ).build()


    //Provide the Run DAO and its functionality
    @Singleton
    @Provides
    fun provideRunDAO(database: RunDatabase) = database.getRunDAO()

}