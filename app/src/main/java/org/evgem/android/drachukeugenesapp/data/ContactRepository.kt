package org.evgem.android.drachukeugenesapp.data

import android.app.Application
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import org.evgem.android.drachukeugenesapp.data.entity.Contact
import org.evgem.android.drachukeugenesapp.util.TAG

object ContactRepository {
    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application

    }

    fun getContact(contactName: String): Contact? {
        val cursor = application.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            "${ContactsContract.Contacts.DISPLAY_NAME} = ?",
            arrayOf(contactName),
            null
        )
        Log.d(TAG, "getContact(), cursor: $cursor")
        return processCursor(cursor)
    }

    private fun processCursor(cursor: Cursor?): Contact? {
        Log.d(TAG, "processCursor()")
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))

            val contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong())
            val photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)

            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

            Log.d(TAG, "query for phone number")
            val phone = application.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                arrayOf(id.toString()),
                null
            )
            Log.d(TAG, "phoneNumberCursor: $phone")
            val phoneNumber: String
            if (phone != null && phone.moveToNext()) {
                phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            } else {
                return null
            }
            phone.close()
            cursor.close()

            return Contact(name, phoneNumber, photoUri)
        }
        cursor?.close()
        return null
    }

    fun getContact(uri: Uri): Contact? {
        val cursor = application.contentResolver.query(uri, null ,null ,null, null)
        Log.d(TAG, "getContact(), cursor: $cursor")
        return processCursor(cursor)
    }
}