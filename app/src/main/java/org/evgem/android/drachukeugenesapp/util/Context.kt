package org.evgem.android.drachukeugenesapp.util

import android.content.Context
import android.content.SharedPreferences

val Context.defaultSharedPreferences: SharedPreferences get() = getSharedPreferences(packageName, Context.MODE_PRIVATE)

val Context.settingsSharedPreferences: SharedPreferences get() = getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE)