package org.evgem.android.cptest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class AppAdapter : RecyclerView.Adapter<AppViewHolder>() {
    var items: ArrayList<Pair<String, String>>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_app, p0, false)
        return AppViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(p0: AppViewHolder, p1: Int) {
        items?.let { items ->
            p0.bind(items[p1].first, items[p1].second)
        }
    }
}