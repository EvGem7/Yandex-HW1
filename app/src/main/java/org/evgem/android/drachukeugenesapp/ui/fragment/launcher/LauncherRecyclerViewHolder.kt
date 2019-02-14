package org.evgem.android.drachukeugenesapp.ui.fragment.launcher

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationRecyclerViewHolder

class LauncherRecyclerViewHolder(itemView: View) :
    ApplicationRecyclerViewHolder(itemView) {
    override fun bind(icon: Drawable?, name: CharSequence) {
        val appView = itemView as TextView
        appView.text = name
        appView.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null)
    }
}