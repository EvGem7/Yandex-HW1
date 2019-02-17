package org.evgem.android.drachukeugenesapp.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "launch")
data class Launch(
    @PrimaryKey
    @ColumnInfo(name = "package_name")
    var packageName: String,

    @ColumnInfo(name = "launch_count")
    var launchCount: Int
)