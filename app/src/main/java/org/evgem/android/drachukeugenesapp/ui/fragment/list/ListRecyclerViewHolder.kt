package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder

class ListRecyclerViewHolder(itemView: View) : ApplicationRecyclerViewHolder(itemView) {
    override fun bind(icon: Drawable?, name: CharSequence) {
        val appView = itemView as TextView
        appView.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        appView.text = name
    }
}