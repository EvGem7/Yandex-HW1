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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setChooserState(AppConfig.getLayout(context))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_layout_chooser, container, false)

        standardRadioButton = view.findViewById(R.id.standard_radio)
        standardBackground = view.findViewById(R.id.standard_background)

        tightRadioButton = view.findViewById(R.id.tight_radio)
        tightBackground = view.findViewById(R.id.tight_background)

        update()

        standardBackground.setOnClickListener {
            chooser = false
            update()
        }
        tightBackground.setOnClickListener {
            chooser = true
            update()
        }

        return view
    }

    private fun getChooserState() = if (chooser) TIGHT else STANDARD

    private fun setChooserState(layout: AppConfig.Layout) {
        chooser = layout == TIGHT
    }

    private fun update() {
        standardRadioButton.isChecked = !chooser
        tightRadioButton.isChecked = chooser

        AppConfig.setLayout(getChooserState(), context)
    }
}