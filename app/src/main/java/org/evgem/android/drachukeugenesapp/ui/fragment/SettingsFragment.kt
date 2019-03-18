package org.evgem.android.drachukeugenesapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.*
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.yandex.metrica.YandexMetrica
import org.evgem.android.drachukeugenesapp.App
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.AppConfig.Layout.STANDARD
import org.evgem.android.drachukeugenesapp.AppConfig.Layout.TIGHT
import org.evgem.android.drachukeugenesapp.AppConfig.Theme.DARK
import org.evgem.android.drachukeugenesapp.AppConfig.Theme.LIGHT
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.network.LoadBackgroundImageAsyncTask
import org.evgem.android.drachukeugenesapp.ui.activity.navigation.NavigationActivity
import org.evgem.android.drachukeugenesapp.util.ReportEvents

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var darkThemeSwitch: SwitchPreferenceCompat
    private lateinit var tightLayoutSwitch: SwitchPreferenceCompat
    private lateinit var runWelcomeActivityCheckBox: CheckBoxPreference
    private lateinit var sortTypeList: ListPreference
    private lateinit var toolbar: Toolbar
    private lateinit var favouritesSwitch: SwitchPreferenceCompat
    private lateinit var updatePreference: Preference
    private lateinit var updateIntervalList: ListPreference
    private lateinit var uniqueBackgroundSwitch: SwitchPreferenceCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            YandexMetrica.reportEvent(ReportEvents.SETTINGS_FRAGMENT_STARTED)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        toolbar = view.findViewById(R.id.settings_toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        val preferencesContainer = view.findViewById<FrameLayout>(R.id.preferences_container)
        val preferencesView = super.onCreateView(inflater, preferencesContainer, savedInstanceState)
        preferencesContainer.addView(preferencesView)
        return view
    }

    override fun onCreatePreferences(savedState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        darkThemeSwitch = findPreference("dark_theme_picker") as SwitchPreferenceCompat
        tightLayoutSwitch = findPreference("tight_layout_picker") as SwitchPreferenceCompat
        runWelcomeActivityCheckBox = findPreference("run_welcome_activity_checkbox") as CheckBoxPreference
        sortTypeList = findPreference("sort_type") as ListPreference
        favouritesSwitch = findPreference("favourite_switch") as SwitchPreferenceCompat
        updatePreference = findPreference("background_image_update")
        updateIntervalList = findPreference("background_image_refresh_rate") as ListPreference
        uniqueBackgroundSwitch = findPreference("background_image_unique") as SwitchPreferenceCompat

        darkThemeSwitch.isChecked = AppConfig.getTheme(context) == DARK
        tightLayoutSwitch.isChecked = AppConfig.getLayout(context) == TIGHT
        runWelcomeActivityCheckBox.isChecked = !AppConfig.isConfigured(context)
        favouritesSwitch.isChecked = AppConfig.isFavouriteShown(context)

        darkThemeSwitch.setOnPreferenceClickListener {
            val theme = if (darkThemeSwitch.isChecked) DARK else LIGHT
            AppConfig.setTheme(theme, context)
            activity?.finish()
            activity?.startActivity(
                Intent(context, NavigationActivity::class.java).apply {
                    putExtra(NavigationActivity.EXTRA_FRAGMENT_TYPE, NavigationActivity.SETTINGS_FRAGMENT)
                }
            )
            YandexMetrica.reportEvent(ReportEvents.THEME_CHANGED)
            return@setOnPreferenceClickListener true
        }
        tightLayoutSwitch.setOnPreferenceClickListener {
            val layout = if (tightLayoutSwitch.isChecked) TIGHT else STANDARD
            AppConfig.setLayout(layout, context)
            YandexMetrica.reportEvent(ReportEvents.LAYOUT_CHANGED)
            return@setOnPreferenceClickListener true
        }
        runWelcomeActivityCheckBox.setOnPreferenceClickListener {
            AppConfig.setConfigured(!runWelcomeActivityCheckBox.isChecked, context)
            YandexMetrica.reportEvent(ReportEvents.RECONFIGURED)
            return@setOnPreferenceClickListener true
        }

        sortTypeList.summary = sortTypeList.entry
        sortTypeList.setOnPreferenceChangeListener { _, newSortType ->
            if (newSortType !is String) {
                return@setOnPreferenceChangeListener true
            }
            AppConfig.applySortType(newSortType)
            val index = sortTypeList.findIndexOfValue(newSortType)
            sortTypeList.summary = sortTypeList.entries[index]
            YandexMetrica.reportEvent(ReportEvents.SORT_CHANGED)
            return@setOnPreferenceChangeListener true
        }
        favouritesSwitch.setOnPreferenceClickListener {
            AppConfig.setFavouriteShown(favouritesSwitch.isChecked, context)
            YandexMetrica.reportEvent(ReportEvents.FAVOURITES_SHOW_CHANGED)
            return@setOnPreferenceClickListener true
        }
        updatePreference.setOnPreferenceClickListener {
            updateBackgroundImage()
            return@setOnPreferenceClickListener true
        }
        updateIntervalList.setOnPreferenceChangeListener { _, interval ->
            (context?.applicationContext as? App)?.scheduleBackgroundImageJob(interval.toString().toLong())
            return@setOnPreferenceChangeListener true
        }
        uniqueBackgroundSwitch.setOnPreferenceChangeListener { _, _ ->
            updateBackgroundImage()
            return@setOnPreferenceChangeListener true
        }
    }

    private fun updateBackgroundImage() {
        context?.let {
            val (portraitUrls, landscapeUrls) = AppConfig.getBackgroundImageUrls(it)
            LoadBackgroundImageAsyncTask(resources, portraitUrls, landscapeUrls).execute()
        }
    }
}