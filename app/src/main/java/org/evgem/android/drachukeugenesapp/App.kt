package org.evgem.android.drachukeugenesapp

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import io.fabric.sdk.android.Fabric
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository
import org.evgem.android.drachukeugenesapp.data.database.LaunchRepository

class App : Application() {
    //TODO("в качестве иконки приложения использовать иконку из вашего профиля")
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        AppCenter.start(this, "4adf0dd6-adf5-41bc-a15b-620487358d07", Analytics::class.java, Crashes::class.java)
        AppConfig.setTheme(AppConfig.getTheme(this), this)
        ApplicationRepository.init(this)
        LaunchRepository.init(this)
    }
}

//TODO recycler forgets its scroll offset after screen rotating
//TODO fix hebrew text