package org.evgem.android.drachukeugenesapp.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class BaseChooserFragment : Fragment() {
    companion object {
        private const val KEY_CHOOSER = "chooser"
    }
    protected var chooser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooser = savedInstanceState?.getBoolean(KEY_CHOOSER) ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(KEY_CHOOSER, chooser)
    }
}