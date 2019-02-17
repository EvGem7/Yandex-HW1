package org.evgem.android.drachukeugenesapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.broadcast.LocaleBroadcastReceiver
import org.evgem.android.drachukeugenesapp.broadcast.PackageBroadcastReceiver
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.data.application.ApplicationRepository
import org.evgem.android.drachukeugenesapp.ui.activity.welcome.WelcomeActivity
import org.evgem.android.drachukeugenesapp.ui.custom.CircularImageView
import org.evgem.android.drachukeugenesapp.ui.fragment.SettingsFragment
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.GridLauncherFragment
import org.evgem.android.drachukeugenesapp.ui.fragment.list.ListFragment
import org.evgem.android.drachukeugenesapp.util.TAG

class NavigationActivity : AppCompatActivity() {
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var avatarImageView: CircularImageView

    private val localeBroadcastReceiver = LocaleBroadcastReceiver()
    private val packageBroadcastReceiver = PackageBroadcastReceiver()

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(packageBroadcastReceiver)
        unregisterReceiver(localeBroadcastReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(packageBroadcastReceiver, packageBroadcastReceiver.intentFilter)
        registerReceiver(localeBroadcastReceiver, localeBroadcastReceiver.intentFilter)

        setContentView(R.layout.activity_navigation)

        fragmentContainer = findViewById(R.id.navigation_fragment_container)
        navigationView = findViewById(R.id.navigation_view)
        drawerLayout = findViewById(R.id.drawer)

        val fragmentType = intent.getIntExtra(EXTRA_FRAGMENT_TYPE, -1)
        setFragment(fragmentType)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.menu_launcher -> setFragment(GRID_FRAGMENT)
                R.id.menu_list -> setFragment(LIST_FRAGMENT)
                R.id.menu_settings -> setFragment(SETTINGS_FRAGMENT)
            }

            return@setNavigationItemSelectedListener true
        }

        avatarImageView = navigationView.getHeaderView(0).findViewById(R.id.header_avatar)
        avatarImageView.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
                .putExtra(WelcomeActivity.EXTRA_SHOW_ONCE, true)
            startActivity(intent)
        }
    }

    private fun setFragment(fragmentType: Int) {
        fun performTransaction(fragment: Fragment) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.navigation_fragment_container)
            if (currentFragment == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.navigation_fragment_container, fragment)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.navigation_fragment_container, fragment)
                    .commit()
            }
        }

        intent.putExtra(EXTRA_FRAGMENT_TYPE, fragmentType)
        when (fragmentType) {
            GRID_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_launcher)
                performTransaction(GridLauncherFragment())
            }
            LIST_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_list)
                performTransaction(ListFragment())
            }
            SETTINGS_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_settings)
                performTransaction(SettingsFragment())
            }
            else -> Log.e(TAG, "unknown fragment type")
        }
    }

    companion object {
        const val EXTRA_FRAGMENT_TYPE = "org.evgem.android.drachukeugenesapp.ui.activity.fragment_type"
        const val GRID_FRAGMENT = 1
        const val LIST_FRAGMENT = 2
        const val SETTINGS_FRAGMENT = 3
    }
}