package org.evgem.android.drachukeugenesapp

import android.content.Context
import android.support.v7.app.AppCompatDelegate
import android.support.v7.app.AppCompatDelegate.*
import org.evgem.android.drachukeugenesapp.util.defaultSharedPreferences
import org.evgem.android.drachukeugenesapp.util.getEnum
import org.evgem.android.drachukeugenesapp.util.putEnum

object AppConfig {
    private const val KEY_THEME = "theme"
    private const val KEY_LAYOUT = "layout"
    private const val KEY_IS_CONFIGURED = "is_configured"

    enum class Theme(val value: Int) {
        LIGHT(0), DARK(1)
    }

    enum class Layout(val portraitIconAmount: Int, val landscapeIconAmount: Int) {
        STANDARD(4, 6), TIGHT(5, 7)
    }

    fun isConfigured(context: Context?): Boolean {
        val sharedPreferences = context?.defaultSharedPreferences
        return sharedPreferences?.getBoolean(KEY_IS_CONFIGURED, false) ?: false
    }

    fun setConfigured(value: Boolean, context: Context?) {
        val sharedPreferences = context?.defaultSharedPreferences
        sharedPreferences?.edit()
            ?.putBoolean(KEY_IS_CONFIGURED, value)
            ?.apply()
    }

    fun getTheme(context: Context?): Theme {
        val sharedPreferences = context?.defaultSharedPreferences
        return sharedPreferences?.getEnum(KEY_THEME, Theme.LIGHT) ?: Theme.LIGHT
    }

    fun setTheme(theme: Theme, context: Context?) {
        val sharedPreferences = context?.defaultSharedPreferences
        sharedPreferences?.edit()
            ?.putEnum(KEY_THEME, theme)
            ?.apply()
        val mode = if (theme == AppConfig.Theme.LIGHT) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun getLayout(context: Context?): Layout {
        val sharedPreferences = context?.defaultSharedPreferences
        return sharedPreferences?.getEnum(KEY_LAYOUT, Layout.STANDARD) ?: Layout.STANDARD
    }

    fun setLayout(layout: Layout, context: Context?) {
        val sharedPreferences = context?.defaultSharedPreferences
        sharedPreferences?.edit()
            ?.putEnum(KEY_LAYOUT, layout)
            ?.apply()
    }

}