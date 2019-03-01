package org.evgem.android.drachukeugenesapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import org.evgem.android.drachukeugenesapp.data.ApplicationRepository

class LocaleBroadcastReceiver : BroadcastReceiver() {
    val intentFilter = IntentFilter().apply {
        addAction(Intent.ACTION_LOCALE_CHANGED)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_LOCALE_CHANGED) {
            ApplicationRepository.loadApps()
        }
    }
}