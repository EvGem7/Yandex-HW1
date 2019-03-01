package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.recycler.ApplicationViewHolder

class ListViewHolder(itemView: View) : ApplicationViewHolder(itemView) {
    override fun bind(app: AppEntity?) {
        super.bind(app)
        app ?: return
        val appView = itemView as TextView
        appView.setCompoundDrawablesWithIntrinsicBounds(app.icon, null, null, null)
        appView.text = app.name
    }
}