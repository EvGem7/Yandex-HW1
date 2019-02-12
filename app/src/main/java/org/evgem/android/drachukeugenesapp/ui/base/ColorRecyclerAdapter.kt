package org.evgem.android.drachukeugenesapp.ui.base

import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import org.evgem.android.drachukeugenesapp.ui.custom.Deleting
import org.evgem.android.drachukeugenesapp.util.getRandomColor

abstract class ColorRecyclerAdapter<VH : ViewHolder>(itemCount: Int) : Adapter<VH>(), Deleting {
    protected val colors = ArrayList<Int>()

    init {
        for (i in 1..itemCount) {
            colors.add(getRandomColor())
        }
    }

    open fun insert(pos: Int, color: Int? = null) {
        colors.add(pos, color ?: getRandomColor())
        notifyItemInserted(pos)
    }

    override fun delete(pos: Int) {
        colors.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun getItemCount(): Int = colors.size
}