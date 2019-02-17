package org.evgem.android.drachukeugenesapp.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import org.evgem.android.drachukeugenesapp.data.database.LaunchRepository
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity

abstract class ApplicationRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun bind(appEntity: AppEntity) {
        itemView.setOnClickListener {
            it.context.startActivity(appEntity.launchIntent)
            LaunchRepository.dao.incrementLaunch(appEntity.packageName)
        }
    }
}