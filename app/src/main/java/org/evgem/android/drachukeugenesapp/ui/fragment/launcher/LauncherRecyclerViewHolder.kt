package org.evgem.android.drachukeugenesapp.ui.fragment.launcher

import android.graphics.drawable.ColorDrawable
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.SnackbarViewHolder
import org.evgem.android.drachukeugenesapp.ui.custom.Deleting
import org.evgem.android.drachukeugenesapp.util.TAG
import org.evgem.android.drachukeugenesapp.util.colorToString

class LauncherRecyclerViewHolder(itemView: View) : SnackbarViewHolder(itemView) {
    fun bind(color: Int, adapter: Deleting) {
        itemView.background = ColorDrawable(color)
        bindSnackbar(color, adapter)
    }
}