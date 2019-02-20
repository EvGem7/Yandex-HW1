package org.evgem.android.drachukeugenesapp.ui.fragment

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.SwitchPreferenceCompat
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.AppConfig.Layout.STANDARD
import org.evgem.android.drachukeugenesapp.AppConfig.Layout.TIGHT
import org.evgem.android.drachukeugenesapp.AppConfig.Theme.DARK
import org.evgem.android.drachukeugenesapp.AppConfig.Theme.LIGHT
import org.evgem.android.drachukeugenesapp.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var darkThemeSwitch: SwitchPreferenceCompat
    private lateinit var tightLayoutSwitch: SwitchPreferenceCompat
    private lateinit var runWelcomeActivityCheckBox: CheckBoxPreference
    private lateinit var sortTypeList: ListPreference
    private lateinit var toolbar: Toolbar

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

        darkThemeSwitch.isChecked = AppConfig.getTheme(context) == DARK
        tightLayoutSwitch.isChecked = AppConfig.getLayout(context) == TIGHT
        runWelcomeActivityCheckBox.isChecked = !AppConfig.isConfigured(context)

        darkThemeSwitch.setOnPreferenceClickListener {
            val theme = if (darkThemeSwitch.isChecked) DARK else LIGHT
            AppConfig.setTheme(theme, context)
            activity?.recreate()
            return@setOnPreferenceClickListener true
        }
        tightLayoutSwitch.setOnPreferenceClickListener {
            val layout = if (tightLayoutSwitch.isChecked) TIGHT else STANDARD
            AppConfig.setLayout(layout, context)
            return@setOnPreferenceClickListener true
        }
        runWelcomeActivityCheckBox.setOnPreferenceClickListener {
            AppConfig.setConfigured(!runWelcomeActivityCheckBox.isChecked, context)
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
            return@setOnPreferenceChangeListener true
        }
    }
}