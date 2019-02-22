package org.evgem.android.drachukeugenesapp.data.database.dao

import android.arch.persistence.room.*
import org.evgem.android.drachukeugenesapp.data.entity.Launch

@Dao
interface LaunchDao {
    @Query("SELECT * FROM launch")
    fun getAll(): List<Launch>

    @Query("SELECT * FROM launch WHERE package_name = :packageName")
    fun getByName(packageName: String): Launch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launch: Launch): Long

    @Query("UPDATE launch SET launch_count = launch_count + 1 WHERE package_name = :packageName")
    fun incrementLaunch(packageName: String)

    @Delete
    fun delete(launch: Launch)

    @Query("DELETE FROM launch WHERE package_name = :packageName")
    fun delete(packageName: String)
}