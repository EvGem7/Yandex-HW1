package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.SnackbarViewHolder
import org.evgem.android.drachukeugenesapp.ui.custom.Deleting

class ListRecyclerViewHolder(itemView: View) : SnackbarViewHolder(itemView) {
    fun bind(color: Int, topic:String, shortMessage: String, adapter: Deleting) {
        val picture: View = itemView.findViewById(R.id.list_picture)
        val topicTextView: TextView = itemView.findViewById(R.id.topic)
        val shortMessageTextView: TextView = itemView.findViewById(R.id.short_message)

        picture.background = GradientDrawable().apply {
            setColor(color)
            shape = GradientDrawable.OVAL
        }
        topicTextView.text = topic
        shortMessageTextView.text = shortMessage

        bindSnackbar(color, adapter)
    }
}