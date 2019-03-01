package org.evgem.android.drachukeugenesapp.data.network

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import org.evgem.android.drachukeugenesapp.data.DesktopRepository
import org.evgem.android.drachukeugenesapp.data.ApplicationRepository
import org.evgem.android.drachukeugenesapp.ui.fragment.desktop.DesktopViewHolder

class LoadDesktopImageAsyncTask(
    private val holder: DesktopViewHolder,
    private val url: String
) : AsyncTask<Void, Void, Drawable?>() {
    override fun doInBackground(vararg params: Void?): Drawable? = loadImage(holder.itemView.resources, url)?.let {
        ApplicationRepository.getIcon(it)
    }

    override fun onPostExecute(result: Drawable?) {
        result?.let {
            DesktopRepository[holder.adapterPosition]?.icon = it
            holder.desktopAdapter.notifyItemChanged(holder.adapterPosition)
        }
    }
}