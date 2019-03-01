package org.evgem.android.drachukeugenesapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.data.ApplicationRepository

class PackageBroadcastReceiver : BroadcastReceiver() {
    val intentFilter
        get() = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }

    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName: String = intent?.dataString?.split(':')?.let { it[1] } ?: return
        when (intent.action) {
            Intent.ACTION_PACKAGE_ADDED -> onPackageInstalled(packageName)
            Intent.ACTION_PACKAGE_REMOVED -> onPackageRemoved(packageName)
        }
    }

    private fun onPackageInstalled(packageName: String?) {
        packageName?.let {
            val index = ApplicationRepository.addApplication(it)
            if (index != -1) {
                ApplicationObservable.onPackageInstalled(packageName, index)
            }
        }
    }

    private fun onPackageRemoved(packageName: String?) {
        packageName?.let {
            val index = ApplicationRepository.removeApplication(it)
            if (index != -1) {
                ApplicationObservable.onPackageRemoved(packageName, index)
            }
        }
    }
}