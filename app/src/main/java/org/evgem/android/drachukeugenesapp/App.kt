package org.evgem.android.drachukeugenesapp

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import io.fabric.sdk.android.Fabric
import org.evgem.android.drachukeugenesapp.data.ContactRepository
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.database.AppDatabase
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

        YandexMetrica.reportEvent(ReportEvents.APPLICATION_STARTED)
    }

    private fun setupMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder("46e901e6-a1c1-441e-9819-01f741876373").build()
        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}

//TODO recycler forgets its scroll offset after screen rotating
//TODO fix hebrew text
//TODO merge favourite and launch tables