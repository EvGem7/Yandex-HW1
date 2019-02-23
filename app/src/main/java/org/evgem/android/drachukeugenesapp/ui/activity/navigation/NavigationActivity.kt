package org.evgem.android.drachukeugenesapp.ui.activity.navigation

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import com.yandex.metrica.YandexMetrica
import de.hdodenhof.circleimageview.CircleImageView
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.broadcast.LocaleBroadcastReceiver
import org.evgem.android.drachukeugenesapp.broadcast.PackageBroadcastReceiver
import org.evgem.android.drachukeugenesapp.ui.base.LockableActivity
import org.evgem.android.drachukeugenesapp.ui.fragment.SettingsFragment
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.GridLauncherFragment
import org.evgem.android.drachukeugenesapp.ui.fragment.list.ListFragment
import org.evgem.android.drachukeugenesapp.ui.fragment.profile.ProfileFragment
import org.evgem.android.drachukeugenesapp.util.ReportEvents
import org.evgem.android.drachukeugenesapp.util.TAG

class NavigationActivity : AppCompatActivity(), LockableActivity {
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var avatarImageView: CircleImageView

    private lateinit var fragmentSwipeController: FragmentSwipeController

    private val localeBroadcastReceiver = LocaleBroadcastReceiver()
    private val packageBroadcastReceiver = PackageBroadcastReceiver()

    override var orientationLocked: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(packageBroadcastReceiver)
        unregisterReceiver(localeBroadcastReceiver)
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            YandexMetrica.reportEvent(ReportEvents.NAVIGATION_ACTIVITY_STARTED)
        }

        registerReceiver(packageBroadcastReceiver, packageBroadcastReceiver.intentFilter)
        registerReceiver(localeBroadcastReceiver, localeBroadcastReceiver.intentFilter)

        setContentView(R.layout.activity_navigation)

        fragmentContainer = findViewById(R.id.navigation_fragment_container)
        navigationView = findViewById(R.id.navigation_view)
        drawerLayout = findViewById(R.id.drawer)

        val fragmentType = intent.getIntExtra(EXTRA_FRAGMENT_TYPE, -1)
        fragmentSwipeController = FragmentSwipeController(this)
        setFragment(fragmentType)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.menu_grid -> setFragment(GRID_FRAGMENT)
                R.id.menu_list -> setFragment(LIST_FRAGMENT)
                R.id.menu_settings -> setFragment(SETTINGS_FRAGMENT)
            }

            return@setNavigationItemSelectedListener true
        }

        avatarImageView = navigationView.getHeaderView(0).findViewById(R.id.header_avatar)
        avatarImageView.setOnClickListener {
            setFragment(PROFILE_FRAGMENT)
            drawerLayout.closeDrawers()
        }

        //for properly work when we pop from back stack
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.navigation_fragment_container)
            when (fragment) {
                is GridLauncherFragment -> {
                    navigationView.setCheckedItem(R.id.menu_grid)
                    intent.putExtra(
                        EXTRA_FRAGMENT_TYPE,
                        GRID_FRAGMENT
                    )
                }
                is ListFragment -> {
                    navigationView.setCheckedItem(R.id.menu_list)
                    intent.putExtra(
                        EXTRA_FRAGMENT_TYPE,
                        LIST_FRAGMENT
                    )
                }
                is SettingsFragment -> {
                    navigationView.setCheckedItem(R.id.menu_settings)
                    intent.putExtra(
                        EXTRA_FRAGMENT_TYPE,
                        SETTINGS_FRAGMENT
                    )
                }
            }
        }
    }

    private fun performTransaction(fragment: Fragment, addToBackStack: Boolean = false) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.navigation_fragment_container)
        val transaction = supportFragmentManager.beginTransaction()
        when {
            currentFragment == null -> transaction
                .add(R.id.navigation_fragment_container, fragment)
                .commit()
            addToBackStack -> transaction
                .replace(R.id.navigation_fragment_container, fragment)
                .addToBackStack(LAUNCHER_BACK_STACK_TAG)
                .commit()
            else -> transaction
                .replace(R.id.navigation_fragment_container, fragment)
                .commit()
        }
    }


    fun setFragment(fragmentType: Int) {
        //when we choose any fragment in menu back stack must be empty. Otherwise fragments can overlap each other
        supportFragmentManager.popBackStackImmediate(LAUNCHER_BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        //put extra in intent after popping, because we have listener that put extra too
        intent.putExtra(EXTRA_FRAGMENT_TYPE, fragmentType)

        when (fragmentType) {
            GRID_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_grid)
                performTransaction(GridLauncherFragment())
            }
            LIST_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_list)
                performTransaction(ListFragment())
            }
            SETTINGS_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_settings)
                performTransaction(SettingsFragment(), true)
            }
            PROFILE_FRAGMENT -> performTransaction(ProfileFragment(), true)
            else -> Log.e(TAG, "unknown fragment type")
        }
        fragmentSwipeController.onSetFragment(fragmentType)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (!orientationLocked) {
            recreate()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GridLauncherFragment.CONTACT_REQUEST_CODE) {
            val fragment = supportFragmentManager.findFragmentById(R.id.navigation_fragment_container)
            if (fragment is GridLauncherFragment) {
                fragment.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    companion object {
        private const val LAUNCHER_BACK_STACK_TAG = "launcher tag"

        const val EXTRA_FRAGMENT_TYPE = "org.evgem.android.drachukeugenesapp.ui.activity.fragment_type"
        const val GRID_FRAGMENT = 1
        const val LIST_FRAGMENT = 2
        const val SETTINGS_FRAGMENT = 3
        const val PROFILE_FRAGMENT = 4
    }
}