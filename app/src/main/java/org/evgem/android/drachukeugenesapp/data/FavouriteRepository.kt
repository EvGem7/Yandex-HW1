package org.evgem.android.drachukeugenesapp.data

import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.database.AppDatabase
import org.evgem.android.drachukeugenesapp.data.database.dao.FavouriteDao
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.data.entity.Favourite
import java.lang.IllegalArgumentException
import java.util.*

object FavouriteRepository {
    private val favouriteAppList =
        ArrayList<AppEntity?>(Collections.nCopies(AppConfig.Layout.TIGHT.landscapeIconAmount, null))
    private lateinit var dao: FavouriteDao
    private const val CONTACT_PACKAGE_NAME = "contact"

    fun init() {
        dao = AppDatabase.INSTANCE.favouriteDao()
        val savedFavourites = dao.getAll()
        for (fav in savedFavourites) {
            when (fav.type) {
                Favourite.APP -> {
                    val app = ApplicationRepository.appList.find { app -> app.packageName == fav.packageName }
                    app?.let { favouriteAppList[fav.index] = app } ?: dao.remove(fav.packageName)
                }
                Favourite.CONTACT -> {
                    TODO("implement contact logic")
                }
                else -> throw IllegalArgumentException("${fav.type} is unknown type for favourite")
            }
        }
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
                    contactName = app.name.toString(),
                    phoneNumber = app.launchIntent.data?.path
                )
            }
            dao.insert(favourite)
        } ?: dao.remove(index)
    }

    operator fun get(index: Int) = favouriteAppList[index]

    fun remove(packageName: String): Int {
        dao.remove(packageName)
        for (i in 0 until favouriteAppList.size) {
            if (favouriteAppList[i]?.packageName == packageName) {
                favouriteAppList[i] = null
                return i
            }
        }
        return -1
    }
}