package com.example.breatheeasy.data.databases.conditions

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.breatheeasy.additional.ContantValues.CURRENT_CONDITIONS_DATABASE_NAME
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditions
import com.example.breatheeasy.data.databases.conditions.current.CurrentConditionsDAO
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocation
import com.example.breatheeasy.data.databases.conditions.location.ConditionsLocationDAO

@Database(
    entities = [CurrentConditions::class, ConditionsLocation::class],
    version = 1
)

abstract class ConditionsDatabase : RoomDatabase() {

    abstract fun getCurrentConditionsDAO() : CurrentConditionsDAO
    abstract fun getConditionsLocationDAO() : ConditionsLocationDAO

    companion object{
        @Volatile private var instance: ConditionsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ConditionsDatabase::class.java, CURRENT_CONDITIONS_DATABASE_NAME)
                .build()
    }
}