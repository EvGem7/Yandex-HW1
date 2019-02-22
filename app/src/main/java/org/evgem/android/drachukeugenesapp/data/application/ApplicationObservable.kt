package org.evgem.android.drachukeugenesapp.data.application

object ApplicationObservable : ApplicationObserver {
    private val observers = ArrayList<ApplicationObserver>()

    override fun onPackageInstalled(packageName: String?, position: Int) {
        for (observer in observers) {
            observer.onPackageInstalled(packageName, position)
        }
    }

    override fun onPackageRemoved(packageName: String?, position: Int) {
        for (observer in observers) {
            observer.onPackageRemoved(packageName, position)
        }
    }

    override fun onSort() {
        for (observer in observers) {
            observer.onSort()
        }
    }

    fun addObserver(observer: ApplicationObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: ApplicationObserver) {
        observers.remove(observer)
    }
}