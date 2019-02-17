package org.evgem.android.drachukeugenesapp.data.application

interface ApplicationObserver {
    fun onPackageInstalled(packageName: String?, position: Int)

    fun onPackageRemoved(packageName: String?, position: Int)
}