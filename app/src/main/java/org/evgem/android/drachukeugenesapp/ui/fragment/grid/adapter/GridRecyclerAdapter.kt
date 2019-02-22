package org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationsRecyclerAdapter
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder.GridRecyclerViewHolder

class GridRecyclerAdapter : ApplicationsRecyclerAdapter() {
    var onFavouriteAdd: ((app: AppEntity) -> Unit)? = null

    override fun onCreateViewHolder(container: ViewGroup, type: Int): ApplicationRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_grid, container, false)
        return GridRecyclerViewHolder(view, this)
    }
}