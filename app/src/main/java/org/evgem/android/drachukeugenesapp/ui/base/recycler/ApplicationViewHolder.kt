package org.evgem.android.drachukeugenesapp.ui.base.recycler

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.View
import android.widget.Toast
import com.yandex.metrica.YandexMetrica
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.DesktopRepository
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.LaunchRepository
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.util.ReportEvents

abstract class ApplicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun bind(app: AppEntity?) {
        app ?: return
        itemView.setOnClickListener { configureOnClickListener(it, app) }
        itemView.setOnCreateContextMenuListener { menu, v, _ ->
            if (menu != null) {
                configureContextMenu(menu, v, app)
            }
        }
    }

    protected open fun configureOnClickListener(view: View, app: AppEntity) {
        view.context?.packageManager?.resolveActivity(app.launchIntent, PackageManager.MATCH_DEFAULT_ONLY)?.let {
            view.context?.startActivity(app.launchIntent)
            YandexMetrica.reportEvent(ReportEvents.APP_LAUNCHED)
            if (app.packageName != FavouriteRepository.CONTACT_PACKAGE_NAME && app.packageName != DesktopRepository.SITE_PACKAGE_NAME) {
                LaunchRepository.incrementLaunch(app.packageName)
            }
        } ?: Toast.makeText(view.context, R.string.cannot_launch, Toast.LENGTH_SHORT).show()
    }

    protected open fun configureContextMenu(menu: ContextMenu, view: View, app: AppEntity) {
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
        YandexMetrica.reportEvent(ReportEvents.APP_DELETED)
        return true
    }

    protected fun onFrequencyAction(view: View, app: AppEntity): Boolean {
        val resources = view.context.resources
        val count = LaunchRepository.getLaunch(app.packageName).launchCount
        Snackbar.make(
            view,
            resources.getString(R.string.snackbar_show_frequency, app.name, count),
            Snackbar.LENGTH_LONG
        ).show()
        YandexMetrica.reportEvent(ReportEvents.FREQUENCY_SHOWED)
        return true
    }

    protected fun onInformationAction(context: Context, packageName: String): Boolean {
        val packageUri = Uri.parse("package:$packageName")
        val infoIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri)
        context.startActivity(infoIntent)
        YandexMetrica.reportEvent(ReportEvents.INFO_SHOWED)
        return true
    }
}