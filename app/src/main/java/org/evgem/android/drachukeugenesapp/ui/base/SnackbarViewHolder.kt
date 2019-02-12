package org.evgem.android.drachukeugenesapp.ui.base

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.custom.Deleting
import org.evgem.android.drachukeugenesapp.util.TAG
import org.evgem.android.drachukeugenesapp.util.colorToString

open class SnackbarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun bindSnackbar(color: Int, adapter: Deleting) {
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