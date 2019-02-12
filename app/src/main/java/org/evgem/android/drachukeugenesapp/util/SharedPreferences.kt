package org.evgem.android.drachukeugenesapp.util

import android.content.SharedPreferences

//TODO improve type safety
inline fun <reified E : Enum<E>> SharedPreferences.getEnum(key: String, defaultValue: E): E {
    val name = getString(key, defaultValue.name) ?: defaultValue.name
    return enumValueOf(name)
}

fun <E : Enum<E>> SharedPreferences.Editor.putEnum(key: String, value: E): SharedPreferences.Editor =
    putString(key, value.name)