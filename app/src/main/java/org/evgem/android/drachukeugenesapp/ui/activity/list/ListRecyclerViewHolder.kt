package org.evgem.android.drachukeugenesapp.ui.activity.list

import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.R

class ListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(color: Int, topic:String, shortMessage: String) {
        val picture: View = itemView.findViewById(R.id.list_picture)
        val topicTextView: TextView = itemView.findViewById(R.id.topic)
        val shortMessageTextView: TextView = itemView.findViewById(R.id.short_message)

        picture.background = GradientDrawable().apply {
            setColor(color)
            shape = GradientDrawable.OVAL
        }
        topicTextView.text = topic
        shortMessageTextView.text = shortMessage
    }
}