package org.evgem.android.drachukeugenesapp

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import io.fabric.sdk.android.Fabric

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        AppCenter.start(this, "4adf0dd6-adf5-41bc-a15b-620487358d07", Analytics::class.java, Crashes::class.java)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}