package org.evgem.android.drachukeugenesapp.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View
import org.evgem.android.drachukeugenesapp.entity.Application

abstract class ApplicationRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(application: Application)
}