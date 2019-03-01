package org.evgem.android.drachukeugenesapp.ui.base

import org.evgem.android.drachukeugenesapp.data.entity.AppEntity

interface AppRequester {
    fun onAppProvided(app: AppEntity)
}