package org.evgem.android.drachukeugenesapp.util

import android.content.Context
import android.content.res.Configuration
import org.evgem.android.drachukeugenesapp.AppConfig

fun getSpanCount(context: Context?) = if (context?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
    AppConfig.getLayout(context).portraitIconAmount
} else {
    AppConfig.getLayout(context).landscapeIconAmount
}