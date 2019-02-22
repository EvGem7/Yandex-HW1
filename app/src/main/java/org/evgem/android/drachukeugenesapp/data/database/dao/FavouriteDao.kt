package org.evgem.android.drachukeugenesapp.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import org.evgem.android.drachukeugenesapp.data.entity.Favourite

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite: Favourite)

    @Query("SELECT * FROM favourite")
    fun getAll(): List<Favourite>

    @Query("DELETE FROM favourite WHERE `index` = :index")
    fun remove(index: Int)

    @Query("DELETE FROM favourite WHERE package_name = :packageName")
    fun remove(packageName: String)
}