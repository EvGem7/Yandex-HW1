package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.entity.Application
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder

class ListRecyclerViewHolder(itemView: View) : ApplicationRecyclerViewHolder(itemView) {
    override fun bind(application: Application) {
        val appView = itemView as TextView
        appView.setCompoundDrawablesWithIntrinsicBounds(application.icon, null, null, null)
        appView.text = application.name
        itemView.setOnClickListener {
            it.context.startActivity(application.launchIntent)
        }
    }
}