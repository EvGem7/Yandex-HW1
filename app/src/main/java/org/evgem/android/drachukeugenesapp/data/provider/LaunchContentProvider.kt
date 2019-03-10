package org.evgem.android.drachukeugenesapp.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import org.evgem.android.drachukeugenesapp.data.database.AppDatabase
import org.evgem.android.drachukeugenesapp.data.database.dao.LaunchDao

class LaunchContentProvider : ContentProvider() {
    private lateinit var dao: LaunchDao


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw SecurityException("operation not permitted")
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = when (matcher.match(uri)) {
        URI_ALL -> dao.getAllCursor()
        URI_LAST -> dao.getLastLaunchedCursor()
        else -> null
    }

    override fun onCreate(): Boolean {
        context?.let { context ->
            if (!AppDatabase.initialized) {
                AppDatabase.init(context)
            }
            dao = AppDatabase.INSTANCE.launchDao()
        } ?: return false
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int =
        when (matcher.match(uri)) {
            URI_UPDATE -> {
                values?.let {
                    val packageName = values.getAsString("package_name")
                    val count = values.getAsInteger("count")
                    dao.update(packageName, count)
                } ?: 0
            }
            else -> 0
        }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw SecurityException("operation not permitted")
    }

    override fun getType(uri: Uri): String? = null

    companion object {
        private const val AUTHORITY = "org.evgem.android.drachukeugenesapp.data.provider.LaunchContentProvider"
        private const val GET_ALL = "all"
        private const val GET_LAST = "last"
        private const val UPDATE = "update"
        private const val URI_ALL = 1
        private const val URI_LAST = 2
        private const val URI_UPDATE = 3


        private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, GET_ALL, URI_ALL)
            addURI(AUTHORITY, GET_LAST, URI_LAST)
            addURI(AUTHORITY, UPDATE, URI_UPDATE)
        }
    }
}