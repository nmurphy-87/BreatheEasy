package com.example.breatheeasy.data.databases.conditions

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.breatheeasy.additional.ContantValues.CURRENT_CONDITIONS_DATABASE_NAME

@Database(
    entities = [CurrentConditions::class],
    version = 1
)

abstract class CurrentConditionsDatabase : RoomDatabase() {

    abstract fun getCurrentConditionsDAO() : CurrentConditionsDAO

    companion object{
        @Volatile private var instance: CurrentConditionsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            CurrentConditionsDatabase::class.java, CURRENT_CONDITIONS_DATABASE_NAME)
                .build()
    }
}