package org.evgem.android.drachukeugenesapp

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.yandex.metrica.push.YandexMetricaPush
import io.fabric.sdk.android.Fabric
import org.evgem.android.drachukeugenesapp.data.ContactRepository
import org.evgem.android.drachukeugenesapp.data.DesktopRepository
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.database.AppDatabase
import org.evgem.android.drachukeugenesapp.data.observer.backimage.BackgroundImageObservable
import org.evgem.android.drachukeugenesapp.service.ImageLoaderJobService
import org.evgem.android.drachukeugenesapp.util.ReportEvents

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        AppCenter.start(this, "4adf0dd6-adf5-41bc-a15b-620487358d07", Analytics::class.java, Crashes::class.java)
        AppConfig.setTheme(AppConfig.getTheme(this), this)
        setupMetrica()

        ContactRepository.init(this)
        AppDatabase.init(this)
        ApplicationRepository.init(this)
        FavouriteRepository.init(this)
        DesktopRepository.init()
        BackgroundImageObservable.init(this)

        YandexMetrica.reportEvent(ReportEvents.APPLICATION_STARTED)

        scheduleBackgroundImageJob()
    }

    private fun setupMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder("46e901e6-a1c1-441e-9819-01f741876373").build()
        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
        YandexMetricaPush.init(applicationContext)
    }

    fun scheduleBackgroundImageJob(period: Long = AppConfig.getBackgroundImageUpdatePeriod(this)) {
        val job = ComponentName(this, ImageLoaderJobService::class.java)
        val jobInfo = JobInfo.Builder(BACKGROUND_IMAGE_JOB_ID, job)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setPeriodic(period)
            .build()
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(jobInfo)
    }

    companion object {
        private const val BACKGROUND_IMAGE_JOB_ID = 0
    }
}

//TODO recycler forgets its scroll offset after screen rotating
//TODO fix hebrew text
//TODO merge favourite and launch tables
//TODO fix contacts