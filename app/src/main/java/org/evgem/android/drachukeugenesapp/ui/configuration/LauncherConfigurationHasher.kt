package org.evgem.android.drachukeugenesapp.ui.configuration

import android.content.Context
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.ui.activity.NavigationActivity
import org.evgem.android.drachukeugenesapp.ui.base.BaseConfigurationHasher

class LauncherConfigurationHasher(private val context: Context?, private val fragmentType: Int) : BaseConfigurationHasher {
    override val hash: Int
        get() {
            val layoutHash = if (fragmentType == NavigationActivity.LAUNCHER_FRAGMENT) {
                AppConfig.getLayout(context).hashCode() + 31 * (context?.resources?.configuration?.orientation ?: 0)
            } else {
                0
            }
            val fragmentTypeHash = 26 * fragmentType
            return layoutHash + fragmentTypeHash
        }
}