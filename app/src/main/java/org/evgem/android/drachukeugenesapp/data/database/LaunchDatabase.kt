package org.evgem.android.drachukeugenesapp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import org.evgem.android.drachukeugenesapp.data.entity.Launch

@Database(entities = [Launch::class], version = 1)
abstract class LaunchDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}