package org.evgem.android.drachukeugenesapp.ui.fragment.profile

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ProfileRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(@DrawableRes drawable: Int, value: String) {
        val tv = itemView as TextView
        tv.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
        tv.text = value
    }
}