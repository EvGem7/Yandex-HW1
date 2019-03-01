package org.evgem.android.drachukeugenesapp.ui.fragment.desktop

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.DesktopRepository
import org.evgem.android.drachukeugenesapp.util.getSpanCount
import kotlin.math.floor

class DesktopAdapter(val activity: AppCompatActivity?) : RecyclerView.Adapter<DesktopViewHolder>() {
    var pendingViewHolder: DesktopViewHolder? = null

    override fun onCreateViewHolder(container: ViewGroup, type: Int): DesktopViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_launcher_grid, container, false)
        return DesktopViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        activity?.resources?.let {
            val rowSize = it.getDimensionPixelSize(R.dimen.row_height)
            val offset = it.getDimensionPixelOffset(R.dimen.recycler_decoration_offset)
            val screenSize = it.displayMetrics.heightPixels
            return floor(screenSize.toDouble() / (2 * offset + rowSize)).toInt() * getSpanCount(activity)
        }
        return 0
    }

    override fun onBindViewHolder(holder: DesktopViewHolder, position: Int) {
        holder.bind(DesktopRepository[position])
    }
}