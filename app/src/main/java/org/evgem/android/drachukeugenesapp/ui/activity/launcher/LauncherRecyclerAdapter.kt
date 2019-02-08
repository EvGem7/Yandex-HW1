package org.evgem.android.drachukeugenesapp.ui.activity.launcher

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import java.util.*

class LauncherRecyclerAdapter : RecyclerView.Adapter<LauncherRecyclerViewHolder>() {
    companion object {
        private const val ITEM_COUNT = 1000
    }

    override fun onCreateViewHolder(container: ViewGroup, type: Int): LauncherRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_icon, container, false)
        return LauncherRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = ITEM_COUNT

    override fun onBindViewHolder(item: LauncherRecyclerViewHolder, pos: Int) {
        val random = Random()
        var color = random.nextInt()
        color = color or 0xFF_00_00_00.toInt()
        item.bind(color)
    }
}