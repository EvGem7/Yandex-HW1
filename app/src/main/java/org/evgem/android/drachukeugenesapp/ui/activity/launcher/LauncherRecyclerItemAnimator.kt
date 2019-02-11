package org.evgem.android.drachukeugenesapp.ui.activity.launcher

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView

class LauncherRecyclerItemAnimator(private val offset: Int) : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.let {
            val view = it.itemView

            val toX = view.x.toInt()
            val fromX = toX - view.measuredWidth - 2 * offset
            val y = view.y.toInt()

            animateMove(holder, fromX, y, toX, y)
        }
        return true
    }
}