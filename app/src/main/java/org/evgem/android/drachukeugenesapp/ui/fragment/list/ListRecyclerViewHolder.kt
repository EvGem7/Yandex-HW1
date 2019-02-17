package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder

class ListRecyclerViewHolder(itemView: View) : ApplicationRecyclerViewHolder(itemView) {
    override fun bind(appEntity: AppEntity) {
        super.bind(appEntity)
        val appView = itemView as TextView
        appView.setCompoundDrawablesWithIntrinsicBounds(appEntity.icon, null, null, null)
        appView.text = appEntity.name
    }
}