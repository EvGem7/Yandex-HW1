package org.evgem.android.drachukeugenesapp.ui.base.recycler

import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import org.evgem.android.drachukeugenesapp.data.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity

abstract class ApplicationAdapter : Adapter<ApplicationViewHolder>() {
    var onClickListener: ((view: View, app: AppEntity?) -> Unit)? = null

    final override fun getItemCount(): Int = ApplicationRepository.appList.size

    final override fun onBindViewHolder(holder: ApplicationViewHolder, pos: Int) {
        val app = ApplicationRepository.appList[pos]
        holder.bind(app)
        onClickListener?.let { onClick -> holder.itemView.setOnClickListener { v -> onClick(v, app) } }
    }
}