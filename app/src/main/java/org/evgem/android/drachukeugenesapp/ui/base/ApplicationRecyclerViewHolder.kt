package org.evgem.android.drachukeugenesapp.ui.base

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.View
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.data.LaunchRepository

abstract class ApplicationRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun bind(app: AppEntity?) {
        app ?: return
        itemView.setOnClickListener {
            it.context.startActivity(app.launchIntent)
            LaunchRepository.incrementLaunch(app.packageName)
        }
        itemView.setOnCreateContextMenuListener { menu, v, _ ->
            if (menu != null) {
                configureContextMenu(menu, v, app)
            }
        }
    }



    companion object {
        fun configureContextMenu(menu: ContextMenu, view: View, app: AppEntity) {
            val resources = view.context.resources

            menu.add(0, view.id, 0, resources.getString(R.string.delete_action))
                .setOnMenuItemClickListener { onDeleteAction(view.context, app.packageName) }

            menu.add(0, view.id, 0, resources.getString(R.string.frequency_action))
                .setOnMenuItemClickListener { onFrequencyAction(view, app) }

            menu.add(0, view.id, 0, resources.getString(R.string.information_action))
                .setOnMenuItemClickListener { onInformationAction(view.context, app.packageName) }
        }

        private fun onDeleteAction(context: Context, packageName: String): Boolean {
            val packageUri = Uri.parse("package:$packageName")
            val uninstallIntent = Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri)
            context.startActivity(uninstallIntent)
            return true
        }

        private fun onFrequencyAction(view: View, app: AppEntity): Boolean {
            val resources = view.context.resources
            val count = LaunchRepository.getLaunch(app.packageName).launchCount
            Snackbar.make(
                view,
                resources.getString(R.string.snackbar_show_frequency, app.name, count),
                Snackbar.LENGTH_LONG
            ).show()
            return true
        }

        private fun onInformationAction(context: Context, packageName: String): Boolean {
            val packageUri = Uri.parse("package:$packageName")
            val infoIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri)
            context.startActivity(infoIntent)
            return true
        }
    }
}