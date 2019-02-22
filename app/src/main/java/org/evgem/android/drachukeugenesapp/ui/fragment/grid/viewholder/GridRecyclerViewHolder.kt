package org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder

import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter.GridRecyclerAdapter

open class GridRecyclerViewHolder(
    itemView: View,
    private val adapter: GridRecyclerAdapter?
) : ApplicationRecyclerViewHolder(itemView) {
    override fun bind(app: AppEntity?) {
        super.bind(app)
        app ?: return

        val appView = itemView as TextView
        appView.text = app.name
        appView.setCompoundDrawablesWithIntrinsicBounds(null, app.icon, null, null)
        appView.setOnCreateContextMenuListener { menu, v, _ ->
            if (adapter != null && menu != null) {
                ApplicationRecyclerViewHolder.configureContextMenu(menu, v, app)
                menu.add(R.string.add_favourite).setOnMenuItemClickListener {
                    adapter.onFavouriteAdd?.invoke(app)
                    true
                }
            }
        }
    }
}