package org.evgem.android.drachukeugenesapp

import android.content.Context
import android.content.res.Configuration
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import org.evgem.android.drachukeugenesapp.data.ApplicationRepository
import org.evgem.android.drachukeugenesapp.util.*

object AppConfig {
    private const val KEY_THEME = "theme"
    private const val KEY_LAYOUT = "layout"
    private const val KEY_IS_CONFIGURED = "is_configured"
    private const val KEY_IS_FAVOURITE_SHOWN = "is_favourite_shown"
    private const val BACKGROUND_IMAGE_UPDATE_PERIOD_DEFAULT = "900000"

    enum class Theme {
        LIGHT, DARK
    }

    enum class Layout(val portraitIconAmount: Int, val landscapeIconAmount: Int) {
        STANDARD(4, 6), TIGHT(5, 7)
    }

    fun isFavouriteShown(context: Context?): Boolean {
        val sharedPreferences = context?.defaultSharedPreferences
        return sharedPreferences?.getBoolean(KEY_IS_FAVOURITE_SHOWN, true) ?: true
    }

    fun setFavouriteShown(value: Boolean, context: Context?) {
        val sharedPreferences = context?.defaultSharedPreferences
        sharedPreferences?.edit()
            ?.putBoolean(KEY_IS_FAVOURITE_SHOWN, value)
            ?.apply()
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

    fun applySortType(sortType: String) {
        when (sortType) {
            "a_z" -> ApplicationRepository.sortAZ()
            "z_a" -> ApplicationRepository.sortZA()
            "launch" -> ApplicationRepository.sortByLaunchCount()
            "date" -> ApplicationRepository.sortByDate()
            "no_sort" -> ApplicationRepository.removeSort()
        }
    }

    /**
     * First is portrait urls.
     * Second is landscape urls.
     */
    fun getBackgroundImageUrls(applicationContext: Context): Pair<List<String>, List<String>> {
        val portraitUrls = mutableListOf<String>()
        val landscapeUrls = mutableListOf<String>()

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            configureImageUrls(portraitUrls, landscapeUrls, applicationContext)

        } else {
            configureImageUrls(landscapeUrls, portraitUrls, applicationContext)
        }
        Log.d(TAG, "portrait urls: $portraitUrls\n landscape urls: $landscapeUrls")
        return portraitUrls to landscapeUrls
    }

    private fun configureImageUrls(
        portraitUrls: MutableList<String>,
        landscapeUrls: MutableList<String>,
        context: Context
    ) {
        val sp = context.settingsSharedPreferences
        val width = context.resources.displayMetrics.widthPixels
        val height = context.resources.displayMetrics.heightPixels

        val sources = sp.getStringSet("background_image_source", null)

        if (sources == null || sources.contains("loremflickr")) {
            portraitUrls += "https://loremflickr.com/$width/$height"
            landscapeUrls += "https://loremflickr.com/$height/$width"
        }

        if (sources == null || sources.contains("picsum")) {
            portraitUrls += "https://picsum.photos/$width/$height/?random"
            landscapeUrls += "https://picsum.photos/$height/$width/?random"
        }

        if (sources == null || sources.contains("lorempixel")) {
            portraitUrls += "http://lorempixel.com/$width/$height"
            landscapeUrls += "http://lorempixel.com/$height/$width"
        }

        if (sources == null || sources.contains("placeimg")) {
            portraitUrls += "https://placeimg.com/$width/$height"
            landscapeUrls += "https://placeimg.com/$height/$width"
        }
    }

    fun getBackgroundImageUpdatePeriod(context: Context?): Long {
        val sharedPreferences = context?.settingsSharedPreferences
        val period = sharedPreferences?.getString("background_image_refresh_rate", BACKGROUND_IMAGE_UPDATE_PERIOD_DEFAULT)
            ?: BACKGROUND_IMAGE_UPDATE_PERIOD_DEFAULT
        Log.d(TAG, "period: $period")
        return period.toLong()
    }

    fun isBackgroundImageUnique(context: Context?): Boolean {
        val sp = context?.settingsSharedPreferences
        return sp?.getBoolean("background_image_unique", true) ?: true
    }
}