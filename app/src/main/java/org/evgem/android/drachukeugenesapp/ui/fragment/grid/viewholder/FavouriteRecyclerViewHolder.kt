package org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder

import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import android.view.ContextMenu
import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter.FavouriteRecyclerAdapter

class FavouriteRecyclerViewHolder(
    itemView: View,
    private val adapter: FavouriteRecyclerAdapter
) : GridRecyclerViewHolder(itemView, null) {
    var newFavouriteAdding = false

    override fun bind(app: AppEntity?) {
        super.bind(app)
        if (app == null) {
            val view = itemView as TextView
            view.text = view.resources.getText(R.string.no_app)
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            if (newFavouriteAdding) {
                var addIcon = ResourcesCompat.getDrawable(view.resources, R.drawable.ic_add, null)
                    ?: throw Resources.NotFoundException("cannot find add icon")
                addIcon = ApplicationRepository.getIcon(addIcon)
                view.setCompoundDrawablesWithIntrinsicBounds(null, addIcon, null, null)

                view.setOnClickListener {
                    super.bind(adapter.currentAddingFavourite)
                    FavouriteRepository[adapterPosition] = adapter.currentAddingFavourite
                    adapter.newFavouriteAdding = false
                }

                view.setOnCreateContextMenuListener { menu, v, _ ->
                    FavouriteRepository[adapterPosition]?.let { configureContextMenu(menu, v, it) }
                }
            }
        } else {
            itemView.setOnCreateContextMenuListener { menu, v, _ -> configureContextMenu(menu, v, app) }
        }
    }

    private fun configureContextMenu(menu: ContextMenu, view: View, app: AppEntity) {
        ApplicationRecyclerViewHolder.configureContextMenu(menu, view, app)
        menu.add(view.resources.getString(R.string.remove_favourite))
            .setOnMenuItemClickListener {
                FavouriteRepository[adapterPosition] = null
                adapter.notifyItemChanged(adapterPosition)
                true
            }
    }
}