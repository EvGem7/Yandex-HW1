package org.evgem.android.drachukeugenesapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.yandex.metrica.push.YandexMetricaPush
import org.evgem.android.drachukeugenesapp.util.ProfileConfig
import org.evgem.android.drachukeugenesapp.util.defaultSharedPreferences

class SilentPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val data = intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD)
            val sp = context.defaultSharedPreferences
            sp.edit()
                .putString(ProfileConfig.GITHUB, data)
                .putString(ProfileConfig.CHAT, data)
                .putString(ProfileConfig.CREDIT_CARD, data)
                .putString(ProfileConfig.EMAIL, data)
                .putString(ProfileConfig.PHONE_NUMBER, data)
                .apply()
        }
    }
}