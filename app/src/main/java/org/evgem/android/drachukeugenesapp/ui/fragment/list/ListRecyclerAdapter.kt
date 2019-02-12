package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.ColorRecyclerAdapter
import org.evgem.android.drachukeugenesapp.util.colorToString
import org.evgem.android.drachukeugenesapp.util.getRandomColor
import org.evgem.android.drachukeugenesapp.util.lastIndex

abstract class ListRecyclerAdapter(itemCount: Int) : ColorRecyclerAdapter<ListRecyclerViewHolder>(itemCount) {
    override fun onCreateViewHolder(container: ViewGroup, type: Int): ListRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_list, container, false)
        return ListRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(item: ListRecyclerViewHolder, pos: Int) {
        val context = item.itemView.context
//        val topics = context.resources.getStringArray(R.array.list_topics)
        val messages = context.resources.getStringArray(R.array.list_messages)
        val color: Int = colors.getOrElse(pos) {
            val color = getRandomColor()
            colors.add(pos, color)
            color
        }
        item.bind(
            color,
            colorToString(color),
            messages[pos % messages.size],
            this
        )
    }
}