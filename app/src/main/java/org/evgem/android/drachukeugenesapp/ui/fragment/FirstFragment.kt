package org.evgem.android.drachukeugenesapp.ui.fragment

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import org.evgem.android.drachukeugenesapp.R

//TODO refactor
sealed class FirstFragment : Fragment() {
    protected lateinit var welcomeImage: CircleImageView
    protected lateinit var welcomeHeader: TextView
    protected lateinit var welcomeText: TextView
    protected lateinit var block: () -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        welcomeImage = view?.findViewById(R.id.welcome_image) ?: return view
        welcomeHeader = view.findViewById(R.id.welcome_header)
        welcomeText = view.findViewById(R.id.welcome_text)

        block()

        return view
    }
}

class WelcomeFragment : FirstFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        block = {
            val drawable = ResourcesCompat.getDrawableForDensity(
                resources,
                R.mipmap.ic_launcher,
                DisplayMetrics.DENSITY_XXHIGH,
                null
            )
            welcomeImage.setImageDrawable(drawable)
            welcomeImage.isDisableCircularTransformation = true
            welcomeHeader.setText(R.string.app_name)
            welcomeText.setText(R.string.welcome_text)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}

class DescriptionFragment : FirstFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        block = {
            welcomeImage.setImageResource(R.drawable.screenshot)
            welcomeImage.isDisableCircularTransformation = false
            welcomeImage.borderWidth = 1
            welcomeImage.borderColor = ResourcesCompat.getColor(resources, R.color.backgroundGray, null)
            val orientation = activity?.resources?.configuration?.orientation
            if (orientation == ORIENTATION_PORTRAIT) {
                welcomeHeader.setText(R.string.app_description)
            } else if (orientation == ORIENTATION_LANDSCAPE) {
                welcomeHeader.setText(R.string.app_name)
                welcomeText.setText(R.string.app_description)
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}