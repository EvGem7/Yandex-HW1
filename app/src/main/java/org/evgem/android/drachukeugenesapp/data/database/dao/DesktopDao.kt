package org.evgem.android.drachukeugenesapp.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import org.evgem.android.drachukeugenesapp.data.entity.DesktopItem

@Dao
interface DesktopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(desktopItem: DesktopItem)

    @Query("SELECT * FROM desktop")
    fun getAll(): List<DesktopItem>

    @Query("DELETE FROM desktop WHERE `index` = :index")
    fun remove(index: Int)

    @Query("DELETE FROM desktop WHERE value = :value")
    fun remove(value: String)
}