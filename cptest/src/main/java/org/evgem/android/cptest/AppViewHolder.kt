package org.evgem.android.cptest

import android.content.ContentValues
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.item_app.view.*

class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(name: String, info: String) {
        itemView.name.text = name
        val count = info.toIntOrNull()
        if (count == null) {//it is date
            itemView.update.visibility = View.GONE
            itemView.count.visibility = View.GONE
            itemView.date.visibility = View.VISIBLE
            itemView.date.text = info
            return
        } else {//it is count
            itemView.date.visibility = View.GONE
            itemView.update.visibility = View.VISIBLE
            itemView.count.visibility = View.VISIBLE
            itemView.count.text.clear()
            itemView.count.text.insert(0, info)
        }
        itemView.update.setOnClickListener {
            try {
                val uri = Uri.parse("content://${MainActivity.AUTHORITY}/update")
                val cv = ContentValues()
                cv.put("package_name", name)
                cv.put("count", itemView.count.text.toString().toInt())
                itemView.context.contentResolver.update(uri, cv, null, null)
            } catch (e: SecurityException) {
                Toast.makeText(itemView.context, "oh no. you need write permission!", Toast.LENGTH_LONG).show()
            }
        }
    }
}