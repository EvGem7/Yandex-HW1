package org.evgem.android.drachukeugenesapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.evgem.android.drachukeugenesapp.ui.activity.navigation.NavigationActivity

class DeeplinkTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, NavigationActivity::class.java).apply {
            putExtra(NavigationActivity.EXTRA_FRAGMENT_TYPE, NavigationActivity.PROFILE_FRAGMENT)
        })
    }
}