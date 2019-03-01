package org.evgem.android.drachukeugenesapp.ui.activity.navigation

object SavedInstanceState {
    private val map = mutableMapOf<String, Any?>()

    operator fun set(key: String, any: Any?) {
        map[key] = any
    }

    operator fun get(key: String) = if (map.contains(key)) map[key] else null
}