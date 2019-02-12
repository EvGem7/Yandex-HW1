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
import org.evgem.android.drachukeugenesapp.ui.custom.CircularImageView
import org.evgem.android.drachukeugenesapp.ui.fragment.launcher.LauncherFragment
import org.evgem.android.drachukeugenesapp.ui.fragment.list.ListFragment
import org.evgem.android.drachukeugenesapp.util.TAG

class NavigationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FRAGMENT_TYPE = "org.evgem.android.drachukeugenesapp.ui.activity.fragment_type"
        const val LAUNCHER_FRAGMENT = 0
        const val LIST_FRAGMENT = 1
    }

    private lateinit var fragmentContainer: FrameLayout
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var avatarImageView: CircularImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        fragmentContainer = findViewById(R.id.navigation_fragment_container)
        navigationView = findViewById(R.id.navigation_view)
        drawerLayout = findViewById(R.id.drawer)

        val fragmentType = intent.getIntExtra(EXTRA_FRAGMENT_TYPE, -1)
        setFragment(fragmentType)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.menu_launcher -> setFragment(LAUNCHER_FRAGMENT)
                R.id.menu_list -> setFragment(LIST_FRAGMENT)
                R.id.menu_settings -> {
                    TODO("implement settings")
                }
            }

            return@setNavigationItemSelectedListener true
        }

        avatarImageView = navigationView.getHeaderView(0).findViewById(R.id.header_avatar)
        avatarImageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setFragment(fragmentType: Int) {
        fun performTransaction(fragment: Fragment) {
            val existingFragment = supportFragmentManager.findFragmentById(R.id.navigation_fragment_container)
            if (existingFragment == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.navigation_fragment_container, fragment)
                    .commit()
            } else {
                //TODO жопой чую можно сделать проще
                if (fragment::class == existingFragment::class) {
                    return
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.navigation_fragment_container, fragment)
                    .commit()
            }
        }

        intent.putExtra(EXTRA_FRAGMENT_TYPE, fragmentType)
        when (fragmentType) {
            LAUNCHER_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_launcher)
                performTransaction(LauncherFragment())
            }
            LIST_FRAGMENT -> {
                navigationView.setCheckedItem(R.id.menu_list)
                performTransaction(ListFragment())
            }
            else -> Log.e(TAG, "unknown fragment type")
        }
    }
}