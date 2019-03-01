package org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder

import android.view.ContextMenu
import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.recycler.ApplicationViewHolder
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter.GridAdapter

open class GridViewHolder(
    itemView: View,
    private val adapter: GridAdapter?
) : ApplicationViewHolder(itemView) {
    override fun bind(app: AppEntity?) {
        super.bind(app)
        app ?: return

        val appView = itemView as TextView
        appView.text = app.name
        appView.setCompoundDrawablesWithIntrinsicBounds(null, app.icon, null, null)
    }

    override fun configureContextMenu(menu: ContextMenu, view: View, app: AppEntity) {
        super.configureContextMenu(menu, view, app)
        if (adapter != null) {
            menu.add(R.string.add_favourite).setOnMenuItemClickListener {
                adapter.onFavouriteAdd?.invoke(app)
                true
            }
        }
    }
}