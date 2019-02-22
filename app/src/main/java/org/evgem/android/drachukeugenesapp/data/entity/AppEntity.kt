package org.evgem.android.drachukeugenesapp.data.entity

import android.content.Intent
import android.graphics.drawable.Drawable
import java.lang.IllegalStateException

data class AppEntity(
    val icon: Drawable?,
    val name: CharSequence,
    val launchIntent: Intent,
    val date: Long,
    val packageName: String = launchIntent.`package` ?: ""
)