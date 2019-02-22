package org.evgem.android.drachukeugenesapp.data

import android.app.Application
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.res.ResourcesCompat
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObserver
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.database.AppDatabase
import org.evgem.android.drachukeugenesapp.data.database.dao.FavouriteDao
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.data.entity.Contact
import org.evgem.android.drachukeugenesapp.data.entity.Favourite
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter.FavouriteRecyclerAdapter
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

object FavouriteRepository : ApplicationObserver {
    private lateinit var application: Application
    private val favouriteAppList =
        ArrayList<AppEntity?>(Collections.nCopies(AppConfig.Layout.TIGHT.landscapeIconAmount, null))
    private lateinit var dao: FavouriteDao
    const val CONTACT_PACKAGE_NAME = "contact"

    var favouriteAdapter: FavouriteRecyclerAdapter? = null

    override fun onPackageRemoved(packageName: String?, position: Int) {
        packageName?.let {
            val result = FavouriteRepository.remove(packageName)
            for (pos in result) {
                favouriteAdapter?.notifyItemChanged(pos)
            }
        }
    }

    fun init(application: Application) {
        this.application = application
        dao = AppDatabase.INSTANCE.favouriteDao()
        val savedFavourites = dao.getAll()
        for (fav in savedFavourites) {
            when (fav.type) {
                Favourite.APP -> {
                    val app = ApplicationRepository.appList.find { app -> app.packageName == fav.packageName }
                    app?.let { favouriteAppList[fav.index] = app } ?: dao.remove(fav.packageName)
                }
                Favourite.CONTACT -> {
                    ContactRepository.getContact(fav.contactName!!)?.let { contact ->
                        favouriteAppList[fav.index] = getAppEntityFromContact(contact)
                    }
                }
                else -> throw IllegalArgumentException("${fav.type} is unknown type for favourite")
            }
        }
        ApplicationObservable.addObserver(this)
    }

    fun getAppEntityFromContact(contact: Contact): AppEntity {
        val name = contact.name
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.phoneNumber}"))

        val image = try {
            val inputStream = application.contentResolver.openInputStream(contact.imageUri)
            Drawable.createFromStream(inputStream, contact.imageUri.toString())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            val defaultIcon = ResourcesCompat.getDrawable(application.resources, R.drawable.ic_contact, null)
                ?: throw Resources.NotFoundException("cannot find default icon")
            ApplicationRepository.getIcon(defaultIcon)
        }
        return AppEntity(image, name, intent, -1, CONTACT_PACKAGE_NAME)
    }

    operator fun set(index: Int, app: AppEntity?) {
        favouriteAppList[index] = app
        app?.let {
            val favourite = if (app.packageName != CONTACT_PACKAGE_NAME) {
                Favourite(
                    index = index,
                    type = Favourite.APP,
                    packageName = app.packageName
                )
            } else {
                Favourite(
                    index = index,
                    type = Favourite.CONTACT,
                    packageName = CONTACT_PACKAGE_NAME,
                    contactName = app.name.toString()
                )
            }
            dao.insert(favourite)
        } ?: dao.remove(index)
    }

    operator fun get(index: Int): AppEntity? {
        return favouriteAppList[index]
    }

    fun remove(packageName: String): List<Int> {
        if (packageName == CONTACT_PACKAGE_NAME) {
            throw IllegalArgumentException("contacts are not allowed for this operation")
        }
        dao.remove(packageName)
        val result = ArrayList<Int>()
        for (i in 0 until favouriteAppList.size) {
            if (favouriteAppList[i]?.packageName == packageName) {
                favouriteAppList[i] = null
                result.add(i)
            }
        }
        return result
    }
}