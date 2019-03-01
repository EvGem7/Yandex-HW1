package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.recycler.ApplicationAdapter

class ListAdapter : ApplicationAdapter() {
    override fun onCreateViewHolder(container: ViewGroup, type: Int): ListViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_list, container, false)
        return ListViewHolder(view)
    }
}