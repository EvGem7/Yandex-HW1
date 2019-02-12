package org.evgem.android.drachukeugenesapp.ui.fragment.launcher

import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.ColorRecyclerAdapter
import org.evgem.android.drachukeugenesapp.ui.custom.Deleting
import org.evgem.android.drachukeugenesapp.util.getRandomColor

abstract class LauncherRecyclerAdapter(itemCount: Int) : ColorRecyclerAdapter<LauncherRecyclerViewHolder>(itemCount), Deleting {
    override fun onCreateViewHolder(container: ViewGroup, type: Int): LauncherRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_icon, container, false)
        return LauncherRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(item: LauncherRecyclerViewHolder, pos: Int) {
        val color: Int = colors.getOrElse(pos) {
            val color = getRandomColor()
            colors.add(pos, color)
            color
        }
        item.bind(color, this)
    }
}