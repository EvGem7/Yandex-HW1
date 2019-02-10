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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_chooser, container, false)

        lightSideRadioButton = view.findViewById(R.id.light_side_radio)
        lightSideBackground = view.findViewById(R.id.light_side_background)

        darkSideRadioButton = view.findViewById(R.id.dark_side_radio)
        darkSideBackground = view.findViewById(R.id.dark_side_background)

        setChooserState()
        if (chooser) {
            darkSideOnClick(null)
        }

        lightSideBackground.setOnClickListener(this::lightSideOnClick)
        darkSideBackground.setOnClickListener(this::darkSideOnClick)

        return view
    }

    private fun lightSideOnClick(view: View?) {
        chooser = false
        setChooserState()

        val orientation = activity?.resources?.configuration?.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            darkSideBackground.background = activity?.getDrawable(R.drawable.shape_border)
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            darkSideBackground.background = ColorDrawable(Color.TRANSPARENT)
        }
    }

    private fun darkSideOnClick(view: View?) {
        chooser = true
        setChooserState()
        darkSideBackground.background = activity?.getDrawable(R.drawable.shape_choosed_dark_border)
    }

    private fun setChooserState() {
        darkSideRadioButton.isChecked = chooser
        lightSideRadioButton.isChecked = !chooser
        AppConfig.appTheme = if (chooser) DARK else LIGHT
    }
}
