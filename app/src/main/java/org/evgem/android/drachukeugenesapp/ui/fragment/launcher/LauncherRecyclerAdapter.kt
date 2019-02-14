package org.evgem.android.drachukeugenesapp.ui.fragment.launcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.configuration.LauncherConfigurationHasher
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationsRecyclerAdapter

class LauncherRecyclerAdapter(configurationHasher: LauncherConfigurationHasher) :
    ApplicationsRecyclerAdapter(configurationHasher) {
    override fun onCreateViewHolder(container: ViewGroup, type: Int): ApplicationRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_grid, container, false)
        return LauncherRecyclerViewHolder(view)
    }

    override fun getIconSize(itemView: View): Int = itemView.measuredWidth
}