package org.evgem.android.drachukeugenesapp.data.network

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import java.lang.Exception
import java.net.URL

fun loadImage(resources: Resources, url: String): BitmapDrawable? = try {
    val inputStream = URL(url).openConnection().getInputStream()
    BitmapDrawable(resources, inputStream).let {
        if (it.bitmap != null) {
            return@let it
        } else {
            Log.e("LoadImage", "bitmap is null")
            return@let null
        }
    }
} catch (e: Exception) {
    Log.e("LoadImage", Log.getStackTraceString(e))
    null
}