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
    private lateinit var darkThemePicker: SwitchPreferenceCompat
    private lateinit var tightLayoutPicker: SwitchPreferenceCompat
    private lateinit var runWelcomeActivityCheckBox: CheckBoxPreference

    override fun onCreatePreferences(savedState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        darkThemePicker = findPreference("dark_theme_picker") as SwitchPreferenceCompat
        tightLayoutPicker = findPreference("tight_layout_picker") as SwitchPreferenceCompat
        runWelcomeActivityCheckBox = findPreference("run_welcome_activity_checkbox") as CheckBoxPreference

        darkThemePicker.isChecked = AppConfig.getTheme(context) == DARK
        tightLayoutPicker.isChecked = AppConfig.getLayout(context) == TIGHT
        runWelcomeActivityCheckBox.isChecked = !AppConfig.isConfigured(context)

        darkThemePicker.setOnPreferenceClickListener {
            val theme = if (darkThemePicker.isChecked) DARK else LIGHT
            AppConfig.setTheme(theme, context)
            activity?.recreate()
            return@setOnPreferenceClickListener true
        }
        tightLayoutPicker.setOnPreferenceClickListener {
            val layout = if (tightLayoutPicker.isChecked) TIGHT else STANDARD
            AppConfig.setLayout(layout, context)
            return@setOnPreferenceClickListener true
        }
        runWelcomeActivityCheckBox.setOnPreferenceClickListener {
            AppConfig.setConfigured(!runWelcomeActivityCheckBox.isChecked, context)
            return@setOnPreferenceClickListener true
        }
    }


}