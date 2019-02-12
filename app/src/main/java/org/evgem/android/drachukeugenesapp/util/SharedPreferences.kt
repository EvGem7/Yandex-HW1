package org.evgem.android.drachukeugenesapp.util

import android.content.SharedPreferences

inline fun <reified E : Enum<E>> SharedPreferences.getEnum(key: String, defaultValue: E): E {
    val defaultString = "${E::class.java.name}@${defaultValue.name}"
    val string = getString(key, defaultString) ?: defaultString
    if (string == defaultString) {
        return defaultValue
    }

    val className: String
    val enum: String
    string.split('@', limit = 2).let {
        className = it[0]
        enum = it[1]
    }
    if (className != E::class.java.name) {
        throw ClassCastException("you are trying to get ${E::class.java.name} but there is $className in SharedPreferences")
    }
    return enumValueOf(enum)
}

inline fun <reified E : Enum<E>> SharedPreferences.Editor.putEnum(key: String, value: E): SharedPreferences.Editor {
    val className = E::class.java.name
    return putString(key, "$className@${value.name}")
}