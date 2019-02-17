package org.evgem.android.drachukeugenesapp.ui.base

import android.support.v7.widget.RecyclerView.Adapter
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository

abstract class ApplicationsRecyclerAdapter : Adapter<ApplicationRecyclerViewHolder>() {
    final override fun getItemCount(): Int = ApplicationRepository.appList.size

    final override fun onBindViewHolder(holder: ApplicationRecyclerViewHolder, pos: Int) {
        holder.bind(ApplicationRepository.appList[pos])
    }
}