package org.evgem.android.drachukeugenesapp.ui.base

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class ApplicationRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(icon: Drawable?, name: CharSequence)
}