package org.evgem.android.drachukeugenesapp.ui.activity.launcher

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import org.evgem.android.drachukeugenesapp.util.colorToString

class LauncherRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(color: Int) {
        itemView.background = ColorDrawable(color)

        itemView.setOnLongClickListener {
            Toast.makeText(itemView.context, colorToString(color), Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }
}