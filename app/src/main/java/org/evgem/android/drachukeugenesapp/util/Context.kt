package org.evgem.android.drachukeugenesapp.util

import android.content.Context

val Context.defaultSharedPreferences get() = getSharedPreferences(packageName, Context.MODE_PRIVATE)