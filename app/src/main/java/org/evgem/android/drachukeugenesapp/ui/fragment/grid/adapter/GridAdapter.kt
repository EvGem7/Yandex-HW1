package org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.recycler.ApplicationViewHolder
import org.evgem.android.drachukeugenesapp.ui.base.recycler.ApplicationAdapter
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder.GridViewHolder

class GridAdapter : ApplicationAdapter() {
    var onFavouriteAdd: ((app: AppEntity) -> Unit)? = null

    override fun onCreateViewHolder(container: ViewGroup, type: Int): ApplicationViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_grid, container, false)
        return GridViewHolder(view, this)
    }
}