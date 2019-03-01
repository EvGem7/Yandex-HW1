package org.evgem.android.drachukeugenesapp.data

import android.app.Application
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.data.entity.Launch
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.util.settingsSharedPreferences
import org.evgem.android.drachukeugenesapp.util.toBitmap

object ApplicationRepository {
    private lateinit var application: Application
    private val packageManager get() = application.packageManager
    private var iconSize = -1
    private var preSortBlock: (() -> Unit)? = null
    private var sortType: Comparator<AppEntity>? = null
    private val launchMap = mutableMapOf<AppEntity, Launch>()

    val appList = ArrayList<AppEntity>()

    private val azSort = Comparator<AppEntity> { o1, o2 ->
        compareValues(o1.name.toString().toLowerCase(), o2.name.toString().toLowerCase())
    }
    private val zaSort = Comparator<AppEntity> { o1, o2 ->
        compareValues(o2.name.toString().toLowerCase(), o1.name.toString().toLowerCase())
    }
    private val dateSort = Comparator<AppEntity> { o1, o2 ->
        compareValues(o1.date, o2.date)
    }
    private val launchSort = Comparator<AppEntity> { o1, o2 ->
        compareValues(launchMap[o2]?.launchCount, launchMap[o1]?.launchCount)
    }

    fun removeSort() {
        sortType = null
        preSortBlock = null
    }

    fun sortAZ() {
        if (sortType == azSort) {
            return
        }
        preSortBlock = null
        sortType =
            azSort
        sort()
    }

    fun sortZA() {
        if (sortType == zaSort) {
            return
        }
        preSortBlock = null
        sortType =
            zaSort
        sort()
    }

    fun sortByDate() {
        if (sortType == dateSort) {
            return
        }
        preSortBlock = null
        sortType =
            dateSort
        sort()
    }

    val sortedByLaunches get() = sortType == launchSort

    fun sortByLaunchCount() {
        sortType =
            launchSort
        preSortBlock = this::updateLaunches
        sort()
    }

    private fun sort(notifyObservable: Boolean = true) {
        preSortBlock?.invoke()
        sortType?.let { sortType -> appList.sortWith(sortType) }
        if (notifyObservable) {
            ApplicationObservable.onSort()
        }
    }

    private fun updateLaunches() {
        val launches = LaunchRepository.launches
        for (app in appList) {
            val launch = launches.find { launch -> launch.packageName == app.packageName }
            launch?.let { launchMap[app] = it }
        }
    }

    fun init(application: Application) {
        ApplicationRepository.application = application
        iconSize = application.resources.getDimensionPixelSize(R.dimen.launcher_icon_size)
        loadApps()

        LaunchRepository.init()
        val sortType = application.settingsSharedPreferences.getString("sort_type", "no_sort") ?: "no_sort"
        AppConfig.applySortType(sortType)
        sort()

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
//        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val name = appInfo.loadLabel(packageManager)
        val icon = getIcon(appInfo)
        val date = packageManager.getPackageInfo(appInfo.packageName, 0).firstInstallTime

        val app = AppEntity(icon, name, launchIntent, date)
        appList.add(app)
        sort(false)
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
        return getIcon(appInfo.loadIcon(packageManager))
    }

    fun getIcon(drawable: Drawable): Drawable {
        val bitmap = Bitmap.createScaledBitmap(drawable.toBitmap(),
            iconSize,
            iconSize, true)
        bitmap.density = application.resources.displayMetrics.densityDpi
        return BitmapDrawable(application.resources, bitmap)
    }
}