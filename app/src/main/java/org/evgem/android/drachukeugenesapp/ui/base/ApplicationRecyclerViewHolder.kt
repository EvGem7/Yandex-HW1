package org.evgem.android.drachukeugenesapp.ui.base

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.View
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.data.launch.LaunchRepository

abstract class ApplicationRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var packageName: String

    open fun bind(app: AppEntity) {
        packageName = app.packageName
        itemView.setOnClickListener {
            it.context.startActivity(app.launchIntent)
            LaunchRepository.incrementLaunch(app.packageName)
        }
        itemView.setOnCreateContextMenuListener { menu, v, _ ->
            val resources = v.context.resources

            menu?.add(0, v.id, 0, resources.getString(R.string.delete_action))
                ?.setOnMenuItemClickListener { onDeleteAction(v.context) }

            menu?.add(0, v.id, 0, resources.getString(R.string.frequency_action))
                ?.setOnMenuItemClickListener { onFrequencyAction(v, app) }

            menu?.add(0, v.id, 0, resources.getString(R.string.information_action))
                ?.setOnMenuItemClickListener { onInformationAction(v.context) }

        }
    }

    private fun onDeleteAction(context: Context): Boolean {
        val packageUri = Uri.parse("package:$packageName")
        val uninstallIntent = Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri)
        context.startActivity(uninstallIntent)
        return true
    }

    private fun onFrequencyAction(view: View, app: AppEntity): Boolean {
        val resources = view.context.resources
        val count = LaunchRepository.getLaunch(packageName).launchCount
        Snackbar.make(
            view,
            resources.getString(R.string.snackbar_show_frequency, app.name, count),
            Snackbar.LENGTH_LONG
        ).show()
        return true
    }

    private fun onInformationAction(context: Context): Boolean {
        val packageUri = Uri.parse("package:$packageName")
        val infoIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri)
        context.startActivity(infoIntent)
        return true
    }
}