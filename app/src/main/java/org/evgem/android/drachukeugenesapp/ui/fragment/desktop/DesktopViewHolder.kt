package org.evgem.android.drachukeugenesapp.ui.fragment.desktop

import android.content.Intent
import android.os.AsyncTask
import android.provider.ContactsContract
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.DesktopRepository
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.data.network.LoadDesktopImageAsyncTask
import org.evgem.android.drachukeugenesapp.ui.base.AppProvider
import org.evgem.android.drachukeugenesapp.ui.base.AppRequester
import org.evgem.android.drachukeugenesapp.ui.dialog.AddSiteDialog
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder.GridViewHolder
import org.evgem.android.drachukeugenesapp.util.TAG

class DesktopViewHolder(
    itemView: View,
    val desktopAdapter: DesktopAdapter
) : GridViewHolder(itemView, null), AppRequester {
    override fun bind(app: AppEntity?) {
        if (app != null) {
            super.bind(app)
            if (app.packageName == DesktopRepository.SITE_PACKAGE_NAME && app.icon == null) {
                val url = "https://favicon.yandex.net/favicon/${getDomainFromApp(app)}/?size=120"
                LoadDesktopImageAsyncTask(this, url).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }
        } else {
            itemView.setOnCreateContextMenuListener { menu, v, _ ->
                menu.add(R.string.add_app).setOnMenuItemClickListener { onAddApplication() }
                menu.add(R.string.add_contact).setOnMenuItemClickListener { onAddContact() }
                menu.add(R.string.add_site).setOnMenuItemClickListener { onAddSite() }
            }
            val tv = itemView as TextView
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            tv.text = null
            tv.setOnClickListener(null)
        }
    }

    override fun configureContextMenu(menu: ContextMenu, view: View, app: AppEntity) {
        menu.add(R.string.delete_action).setOnMenuItemClickListener { onDeleteAction() }
        if (app.packageName != FavouriteRepository.CONTACT_PACKAGE_NAME && app.packageName != DesktopRepository.SITE_PACKAGE_NAME) {
            menu.add(R.string.frequency_action).setOnMenuItemClickListener { onFrequencyAction(view, app) }
            menu.add(R.string.information_action)
                .setOnMenuItemClickListener { onInformationAction(view.context, app.packageName) }
        }
    }

    private fun onDeleteAction(): Boolean {
        Log.d(TAG, "onDeleteAction: adapter position: $adapterPosition")
        DesktopRepository[adapterPosition] = null
        desktopAdapter.notifyItemChanged(adapterPosition)
        return true
    }

    private fun onAddSite(): Boolean {
        val fm = desktopAdapter.activity?.supportFragmentManager
        val adapterPosition = adapterPosition
        AddSiteDialog.newInstance { url ->
            val intent = Intent(Intent.ACTION_VIEW, DesktopRepository.getUriFromUrl(url))
            val app = AppEntity(null, url, intent, -1, DesktopRepository.SITE_PACKAGE_NAME)

            Log.d(TAG, "onAddSite: adapter position: $adapterPosition")
            DesktopRepository[adapterPosition] = app
            desktopAdapter.notifyItemChanged(adapterPosition)
        }.apply {
            retainInstance = true
            show(fm, TAG)
        }
        return true
    }

    private fun onAddContact(): Boolean {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        desktopAdapter.activity?.startActivityForResult(intent, DesktopFragment.CONTACT_REQUEST_CODE)
        desktopAdapter.pendingViewHolder = this
        return true
    }

    private fun onAddApplication(): Boolean {
        (desktopAdapter.activity as? AppProvider)?.onAppRequested(this)
        return true
    }

    override fun onAppProvided(app: AppEntity) {
        Log.d(TAG, "onAppProvided: adapter position: $adapterPosition")
        DesktopRepository[adapterPosition] = app
    }

    private fun getDomainFromApp(app: AppEntity): String? {
        val regex = """^(https?://)?([\w.]*).*""".toRegex()
        return if (regex matches app.name) {
            val matchResult = regex.matchEntire(app.name) ?: return null
            Log.d(TAG, "site: ${app.name}. group count: ${matchResult.groupValues.size}")
            return matchResult.groupValues[2]
        } else {
            null
        }
    }
}