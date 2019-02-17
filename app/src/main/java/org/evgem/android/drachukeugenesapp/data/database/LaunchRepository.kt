package org.evgem.android.drachukeugenesapp.data.database

import android.arch.persistence.room.Room
import android.content.Context
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObserver
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.entity.Launch

object LaunchRepository : ApplicationObserver {
    private lateinit var database: LaunchDatabase
    private const val DATABASE_NAME = "launch_database"

    val dao: LaunchDao get() = database.launchDao()
    val launches: List<Launch> get() = dao.getAll()

    fun init(context: Context) {
        database = Room.databaseBuilder(context, LaunchDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
        val currentLaunches = launches
        for (app in ApplicationRepository.appList) {
            val launch = currentLaunches.find { launch -> launch.packageName == app.packageName }
            if (launch == null) {
                dao.insertAll(Launch(app.packageName, 0))
            }
        }
        ApplicationObservable.addObserver(this)
    }

    override fun onPackageInstalled(packageName: String?, position: Int) {
        packageName?.let {
            dao.insertAll(Launch(it, 0))
        }
    }

    override fun onPackageRemoved(packageName: String?, position: Int) {
        packageName?.let { dao.delete(it) }
    }
}