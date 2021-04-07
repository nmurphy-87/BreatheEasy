package com.example.breatheeasy.data.databases.run

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Run::class],
    version = 1
)

@TypeConverters(Convertors::class)
abstract class RunDatabase : RoomDatabase() {

    abstract fun getRunDAO(): RunDAO
}