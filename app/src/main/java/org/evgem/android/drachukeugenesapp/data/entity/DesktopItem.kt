package org.evgem.android.drachukeugenesapp.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "desktop")
data class DesktopItem(
    @PrimaryKey
    @ColumnInfo(name = "index")
    val index: Int,

    @ColumnInfo(name = "type")
    val type: Int,

    @ColumnInfo(name = "value")
    val value: String
) {
    companion object {
        const val APP = 0
        const val CONTACT = 1
        const val SITE = 2
    }
}