package org.evgem.android.drachukeugenesapp.ui.fragment

import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.SwitchPreferenceCompat
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

    override fun onCreatePreferences(savedState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        darkThemeSwitch = findPreference("dark_theme_picker") as SwitchPreferenceCompat
        tightLayoutSwitch = findPreference("tight_layout_picker") as SwitchPreferenceCompat
        runWelcomeActivityCheckBox = findPreference("run_welcome_activity_checkbox") as CheckBoxPreference

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
    }


}