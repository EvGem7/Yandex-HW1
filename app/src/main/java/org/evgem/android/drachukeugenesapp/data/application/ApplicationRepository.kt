package org.evgem.android.drachukeugenesapp.data.application

import android.app.Application
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity

object ApplicationRepository {
    private lateinit var application: Application
    private val packageManager get() = application.packageManager
    private var iconSize = -1
    private var sortType: Comparator<AppEntity>? = null
    val appList = ArrayList<AppEntity>()

    fun removeSort() {
        sortType = null
    }

    fun sortAZ() {
        sortType = Comparator { o1, o2 -> compareValues(o1.name.toString(), o2.name.toString()) }
        sort()
    }

    fun sortZA() {
        sortType = Comparator { o1, o2 -> compareValues(o2.name.toString(), o1.name.toString()) }
        sort()
    }

    private fun sort() {
        sortType?.let { sortType -> appList.sortWith(sortType) }
    }

    fun init(application: Application) {
        ApplicationRepository.application = application
        iconSize = application.resources.getDimensionPixelSize(R.dimen.launcher_icon_size)
        loadApps()
    }

    fun loadApps() {
        appList.clear()

        val applicationInfoList = packageManager.getInstalledApplications(0)
        for (appInfo in applicationInfoList) {
            addApplication(appInfo)
        }
        sort()
    }

    fun addApplication(appInfo: ApplicationInfo): Int {
        val launchIntent = packageManager.getLaunchIntentForPackage(appInfo.packageName) ?: return -1
        val name = appInfo.loadLabel(packageManager)
        val icon = getIcon(appInfo)

        val app = AppEntity(icon, name, launchIntent)
        appList.add(app)
        sort()
        return appList.indexOf(app)
    }

    fun addApplication(packageName: String): Int {
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        return addApplication(appInfo)
    }

    fun removeApplication(packageName: String): Int {
        var index = -1
        for (i in 0 until appList.size) {
            if (appList[i].packageName == packageName) {
                appList.removeAt(i)
                index = i
                break
            }
        }
        return index
    }

    private fun getIcon(appInfo: ApplicationInfo): Drawable? {
        var icon = appInfo.loadIcon(packageManager)
        val bitmapIcon = icon as? BitmapDrawable
        bitmapIcon?.let {
            val bitmap = Bitmap.createScaledBitmap(bitmapIcon.bitmap, iconSize, iconSize, true)
            icon = BitmapDrawable(application.resources, bitmap)
        }
        return icon
    }
}