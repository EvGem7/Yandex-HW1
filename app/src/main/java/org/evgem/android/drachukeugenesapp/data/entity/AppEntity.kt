package org.evgem.android.drachukeugenesapp.data.entity

import android.content.Intent
import android.graphics.drawable.Drawable
import java.lang.IllegalStateException

data class AppEntity(
    var icon: Drawable?,
    val name: CharSequence,
    val launchIntent: Intent,
    val date: Long,
    val packageName: String = launchIntent.`package` ?: ""
)