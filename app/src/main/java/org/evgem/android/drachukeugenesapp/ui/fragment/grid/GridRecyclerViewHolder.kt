package org.evgem.android.drachukeugenesapp.ui.fragment.grid

import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder

class GridRecyclerViewHolder(itemView: View) : ApplicationRecyclerViewHolder(itemView) {
    override fun bind(app: AppEntity) {
        super.bind(app)
        val appView = itemView as TextView
        appView.text = app.name
        appView.setCompoundDrawablesWithIntrinsicBounds(null, app.icon, null, null)
    }
}