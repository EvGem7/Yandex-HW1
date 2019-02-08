package org.evgem.android.drachukeugenesapp.ui.fragment

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.AppConfig.Layout.STANDARD
import org.evgem.android.drachukeugenesapp.AppConfig.Layout.TIGHT
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.BaseChooserFragment

class LayoutChooserFragment : BaseChooserFragment() {
    private lateinit var standardRadioButton: RadioButton
    private lateinit var standardBackground: ConstraintLayout

    private lateinit var tightRadioButton: RadioButton
    private lateinit var tightBackground: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_layout_chooser, container, false)

        standardRadioButton = view.findViewById(R.id.standard_radio)
        standardBackground = view.findViewById(R.id.standard_background)

        tightRadioButton = view.findViewById(R.id.tight_radio)
        tightBackground = view.findViewById(R.id.tight_background)

        setChooserState()

        standardBackground.setOnClickListener {
            chooser = false
            setChooserState()
        }
        tightBackground.setOnClickListener {
            chooser = true
            setChooserState()
        }

        return view
    }

    private fun setChooserState() {
        standardRadioButton.isChecked = !chooser
        tightRadioButton.isChecked = chooser
        AppConfig.layout = if (chooser) TIGHT else STANDARD
    }
}