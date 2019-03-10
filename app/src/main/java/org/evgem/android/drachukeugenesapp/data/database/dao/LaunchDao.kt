package org.evgem.android.drachukeugenesapp.data.database.dao

import android.arch.persistence.room.*
import android.database.Cursor
import org.evgem.android.drachukeugenesapp.data.entity.Launch

@Dao
interface LaunchDao {
    @Query("SELECT * FROM launch")
    fun getAll(): List<Launch>

    @Query("SELECT * FROM launch WHERE package_name = :packageName")
    fun getByName(packageName: String): Launch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launch: Launch): Long

    @Query("UPDATE launch SET launch_count = launch_count + 1, last_launched = :date WHERE package_name = :packageName")
    fun incrementLaunch(packageName: String, date: Long)

    @Delete
    fun delete(launch: Launch)

    @Query("DELETE FROM launch WHERE package_name = :packageName")
    fun delete(packageName: String)

    @Query("SELECT * FROM launch WHERE launch_count > 0")
    fun getAllCursor(): Cursor

    @Query("SELECT * FROM launch ORDER BY last_launched DESC LIMIT 1")
    fun getLastLaunchedCursor(): Cursor

    @Query("UPDATE launch SET launch_count = :count WHERE package_name = :packageName")
    fun update(packageName: String, count: Int): Int
}