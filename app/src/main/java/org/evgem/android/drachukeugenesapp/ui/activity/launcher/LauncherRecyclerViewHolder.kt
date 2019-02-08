package org.evgem.android.drachukeugenesapp.ui.activity.launcher

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast

class LauncherRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(color: Int) {
        itemView.background = ColorDrawable(color)

        val colorString = String.format("#%06X", 0xFFFFFF and color)
        itemView.setOnLongClickListener {
            Toast.makeText(itemView.context, colorString, Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }
}