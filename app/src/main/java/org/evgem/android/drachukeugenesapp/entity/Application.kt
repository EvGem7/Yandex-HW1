package org.evgem.android.drachukeugenesapp.entity

import android.content.Intent
import android.graphics.drawable.Drawable

data class Application(
    var icon: Drawable?,
    var name: CharSequence,
    val launchIntent: Intent
)