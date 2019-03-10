package org.evgem.android.drachukeugenesapp.data

import android.database.Cursor
import android.util.Log
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObserver
import org.evgem.android.drachukeugenesapp.data.database.dao.LaunchDao
import org.evgem.android.drachukeugenesapp.data.database.AppDatabase
import org.evgem.android.drachukeugenesapp.data.entity.Launch
import org.evgem.android.drachukeugenesapp.util.TAG
import java.util.*

object LaunchRepository : ApplicationObserver {
    private val database: AppDatabase get() = AppDatabase.INSTANCE

    private val dao: LaunchDao get() = database.launchDao()
    val launches: List<Launch> get() = dao.getAll()
    private var initialized = false

    fun init() {
        val currentLaunches = launches
        for (app in ApplicationRepository.appList) {
            val launch = currentLaunches.find { launch -> launch.packageName == app.packageName }
            if (launch == null) {
                dao.insert(Launch(app.packageName, 0))
            }
        }
        for (launch in currentLaunches) {
            val app = ApplicationRepository.appList.find { app -> app.packageName == launch.packageName }
            if (app == null) {
                dao.delete(launch)
            }
        }
        ApplicationObservable.addObserver(this)
        initialized = true
    }

    fun incrementLaunch(packageName: String) {
        val date: Long = System.currentTimeMillis()
        dao.incrementLaunch(packageName, date)
        if (ApplicationRepository.sortedByLaunches) {
            ApplicationRepository.sortByLaunchCount()
        }
    }

    fun getLaunch(packageName: String) = dao.getByName(packageName)

    override fun onPackageInstalled(packageName: String?, position: Int) {
        packageName?.let {
            dao.insert(Launch(it, 0))
        }
    }

    override fun onPackageRemoved(packageName: String?, position: Int) {
        packageName?.let { dao.delete(it) }
    }
}