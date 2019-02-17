package org.evgem.android.drachukeugenesapp.data.application

object ApplicationObservable {
    private val observers = ArrayList<ApplicationObserver>()

    fun onPackageInstalled(packageName: String?, position: Int) {
        for (observer in observers) {
            observer.onPackageInstalled(packageName, position)
        }
    }

    fun onPackageRemoved(packageName: String?, position: Int) {
        for (observer in observers) {
            observer.onPackageRemoved(packageName, position)
        }
    }

    fun addObserver(observer: ApplicationObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: ApplicationObserver) {
        observers.remove(observer)
    }
}