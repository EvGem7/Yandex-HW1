package org.evgem.android.drachukeugenesapp.ui.fragment.launcher

import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.entity.Application
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder

class LauncherRecyclerViewHolder(itemView: View) :
    ApplicationRecyclerViewHolder(itemView) {
    override fun bind(application: Application) {
        val appView = itemView as TextView
        appView.text = application.name
        appView.setCompoundDrawablesWithIntrinsicBounds(null, application.icon, null, null)
        itemView.setOnClickListener {
            it.context.startActivity(application.launchIntent)
        }
    }
}