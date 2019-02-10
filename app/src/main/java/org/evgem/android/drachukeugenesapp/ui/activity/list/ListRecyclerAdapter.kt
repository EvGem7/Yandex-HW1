package org.evgem.android.drachukeugenesapp.ui.activity.list

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.AutoGeneratedRecyclerAdapter
import org.evgem.android.drachukeugenesapp.util.colorToString
import org.evgem.android.drachukeugenesapp.util.getRandomColor
import org.evgem.android.drachukeugenesapp.util.lastIndex

class ListRecyclerAdapter(itemCount: Int) : AutoGeneratedRecyclerAdapter<ListRecyclerViewHolder>(itemCount) {
    private val colors: SparseArray<Int> = SparseArray()

    override fun onCreateViewHolder(container: ViewGroup, type: Int): ListRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_list, container, false)
        return ListRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(item: ListRecyclerViewHolder, pos: Int) {
        val context = item.itemView.context
//        val topics = context.resources.getStringArray(R.array.list_topics)
        val messages = context.resources.getStringArray(R.array.list_messages)
        val color = colors.get(pos, getRandomColor())
        item.bind(
            color,
            colorToString(color),
            messages[(lastIndex - pos) % messages.size]
        )
        colors.put(pos, color)
    }
}