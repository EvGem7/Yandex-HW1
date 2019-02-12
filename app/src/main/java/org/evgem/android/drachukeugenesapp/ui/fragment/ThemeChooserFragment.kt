package org.evgem.android.drachukeugenesapp.ui.fragment


import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RadioButton
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.AppConfig.Theme.DARK
import org.evgem.android.drachukeugenesapp.AppConfig.Theme.LIGHT
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.BaseChooserFragment

class ThemeChooserFragment : BaseChooserFragment() {

    private lateinit var lightSideRadioButton: RadioButton
    private lateinit var lightSideBackground: FrameLayout

    private lateinit var darkSideRadioButton: RadioButton
    private lateinit var darkSideBackground: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setChooserState(AppConfig.getTheme(context))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_chooser, container, false)

        lightSideRadioButton = view.findViewById(R.id.light_side_radio)
        lightSideBackground = view.findViewById(R.id.light_side_background)

        darkSideRadioButton = view.findViewById(R.id.dark_side_radio)
        darkSideBackground = view.findViewById(R.id.dark_side_background)

        updateUI()

        lightSideBackground.setOnClickListener {
            chooser = false
            updateUI()
            updateTheme()
        }
        darkSideBackground.setOnClickListener {
            chooser = true
            updateUI()
            updateTheme()
        }

        return view
    }

    private fun getChooserState() = if (chooser) DARK else LIGHT

    private fun setChooserState(layout: AppConfig.Theme) {
        chooser = layout == DARK
    }

    private fun updateUI() {
        darkSideRadioButton.isChecked = chooser
        lightSideRadioButton.isChecked = !chooser
    }

    private fun updateTheme() {
        AppConfig.setTheme(getChooserState(), context)
        activity?.recreate()
    }
}
