package org.evgem.android.drachukeugenesapp

import android.content.Context
import org.evgem.android.drachukeugenesapp.util.defaultSharedPreferences
import org.evgem.android.drachukeugenesapp.util.getEnum
import org.evgem.android.drachukeugenesapp.util.putEnum

object AppConfig {
    private const val KEY_THEME = "org.evgem.android.drachukeugenesapp.theme"
    private const val KEY_LAYOUT = "org.evgem.android.drachukeugenesapp.layout"

    enum class Theme(val value: Int) {
        LIGHT(0), DARK(1)
    }

    enum class Layout(val portraitIconAmount: Int, val landscapeIconAmount: Int) {
        STANDARD(4, 6), TIGHT(5, 7)
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