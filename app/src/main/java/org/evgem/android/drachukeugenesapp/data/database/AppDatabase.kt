package org.evgem.android.drachukeugenesapp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import org.evgem.android.drachukeugenesapp.data.database.dao.DesktopDao
import org.evgem.android.drachukeugenesapp.data.database.dao.FavouriteDao
import org.evgem.android.drachukeugenesapp.data.database.dao.LaunchDao
import org.evgem.android.drachukeugenesapp.data.entity.DesktopItem
import org.evgem.android.drachukeugenesapp.data.entity.Favourite
import org.evgem.android.drachukeugenesapp.data.entity.Launch

@Database(entities = [Launch::class, Favourite::class, DesktopItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
    abstract fun favouriteDao(): FavouriteDao
    abstract fun desktopDao(): DesktopDao

    companion object {
        lateinit var INSTANCE: AppDatabase

        fun init(context: Context) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        }

        private const val DATABASE_NAME = "database"
    }
}