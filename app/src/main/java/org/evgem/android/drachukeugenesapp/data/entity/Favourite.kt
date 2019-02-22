package org.evgem.android.drachukeugenesapp.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey
    @ColumnInfo(name = "index")
    val index: Int,

    @ColumnInfo(name = "type")
    val type: Int,

    @ColumnInfo(name = "package_name")
    val packageName: String,

    @ColumnInfo(name = "contact_name")
    val contactName: String? = null,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = null
) {
    companion object {
        const val CONTACT = 0
        const val APP = 1
    }
}