package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationsRecyclerAdapter

class ListRecyclerAdapter : ApplicationsRecyclerAdapter() {
    override fun onCreateViewHolder(container: ViewGroup, type: Int): ListRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_list, container, false)
        return ListRecyclerViewHolder(view)
    }
}