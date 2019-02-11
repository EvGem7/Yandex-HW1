package org.evgem.android.drachukeugenesapp.ui.activity.launcher

import android.graphics.drawable.ColorDrawable
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.Callback.*
import android.support.design.widget.SwipeDismissBehavior
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.util.TAG
import org.evgem.android.drachukeugenesapp.util.colorToString

class LauncherRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(color: Int, adapter: LauncherRecyclerAdapter) {
        itemView.background = ColorDrawable(color)

        itemView.setOnLongClickListener {
            val snackbarText = itemView.context.resources.getString(R.string.color_snackbar, colorToString(color))
            Snackbar.make(itemView, snackbarText, 5000)
                .setAction(R.string.delete_action) { adapter.delete(adapterPosition) }
                .addCallback(object : Snackbar.Callback() {
                    override fun onShown(sb: Snackbar?) {
                        Log.i(TAG, "snackbar shown")
                    }

                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        val message = when (event) {
                            DISMISS_EVENT_ACTION -> "dismissed by action"
                            DISMISS_EVENT_TIMEOUT -> "dismissed by timeout"
                            DISMISS_EVENT_SWIPE -> "dismissed by swipe"
                            else -> "dismissed by something"
                        }
                        Log.i(TAG, message)
                    }
                }).show()
            return@setOnLongClickListener true
        }
    }
}