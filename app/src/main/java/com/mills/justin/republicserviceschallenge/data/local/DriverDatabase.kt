package com.mills.justin.republicserviceschallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EntityDriver::class, EntityRoute::class], version = 1)
abstract class DriverDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
}