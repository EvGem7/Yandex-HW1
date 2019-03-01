package org.evgem.android.drachukeugenesapp.data

import android.content.Intent
import android.net.Uri
import android.util.SparseArray
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObserver
import org.evgem.android.drachukeugenesapp.data.database.AppDatabase
import org.evgem.android.drachukeugenesapp.data.database.dao.DesktopDao
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.data.entity.DesktopItem
import java.util.*

object DesktopRepository : ApplicationObserver {
    const val SITE_PACKAGE_NAME = "site"

    private val desktopList = SparseArray<AppEntity?>()
    private lateinit var dao: DesktopDao

    var notifyAdapter: ((changedItems: List<Int>) -> Unit)? = null

    override fun onPackageRemoved(packageName: String?, position: Int) {
        val changedItems = mutableListOf<Int>()
        for (i in 0 until desktopList.size()) {
            if (desktopList.valueAt(i)?.packageName == packageName) {
                val key = desktopList.keyAt(i)
                this[key] = null
                changedItems.add(key)
            }
        }
        notifyAdapter?.invoke(changedItems)
    }

    fun init() {
        dao = AppDatabase.INSTANCE.desktopDao()
        val savedDesktopItems = dao.getAll()
        for (desktop in savedDesktopItems) {
            when (desktop.type) {
                DesktopItem.APP -> {
                    val app = ApplicationRepository.appList.find { app -> app.packageName == desktop.value }
                    app?.let { desktopList.put(desktop.index, it) } ?: dao.remove(desktop.index)
                }
                DesktopItem.CONTACT -> {
                    ContactRepository.getContact(desktop.value)?.let { contact ->
                        desktopList.put(desktop.index, FavouriteRepository.getAppEntityFromContact(contact))
                    }
                }
                DesktopItem.SITE -> {
                    val intent = Intent(Intent.ACTION_VIEW, getUriFromUrl(desktop.value))
                    desktopList.put(desktop.index, AppEntity(null, desktop.value, intent, -1, SITE_PACKAGE_NAME))
                }
                else -> throw UnknownFormatConversionException("unknown DesktopItem type")
            }
        }
        ApplicationObservable.addObserver(this)
    }

    operator fun get(position: Int) = desktopList[position]

    operator fun set(position: Int, app: AppEntity?) {
        desktopList.put(position, app)
        app?.let { dao.insert(getDesktopItemFromApp(app, position)) } ?: dao.remove(position)
    }

    fun getUriFromUrl(url: String): Uri = Uri.parse(
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "http://$url"
        } else {
            url
        }
    )

    private fun getDesktopItemFromApp(app: AppEntity, index: Int): DesktopItem {
        val type: Int
        val value: String
        when (app.packageName) {
            FavouriteRepository.CONTACT_PACKAGE_NAME -> {
                type = DesktopItem.CONTACT
                value = app.name.toString()
            }
            SITE_PACKAGE_NAME -> {
                type = DesktopItem.SITE
                value = app.name.toString()
            }
            else -> {
                type = DesktopItem.APP
                value = app.packageName
            }
        }
        return DesktopItem(index, type, value)
    }
}