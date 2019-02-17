package org.evgem.android.drachukeugenesapp.ui.activity.welcome


import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.activity.NavigationActivity
import org.evgem.android.drachukeugenesapp.ui.custom.OnConfigurationChangedListener

class WelcomeActivity : AppCompatActivity(), OnConfigurationChangedListener {
    private var currentFragment = 0
    private var configurationChanged = false
    private val welcomePagerAdapter = WelcomePagerAdapter(supportFragmentManager)

    private lateinit var fragmentContainer: ViewPager
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isShowedOnce = intent.getBooleanExtra(EXTRA_SHOW_ONCE, false)
        if (AppConfig.isConfigured(this) && !isShowedOnce) {
            startNavigationActivity()
        }

        setContentView(R.layout.activity_welcome)

        fragmentContainer = findViewById(R.id.welcome_fragment_container)
        fragmentContainer.adapter = welcomePagerAdapter
        fragmentContainer.offscreenPageLimit = welcomePagerAdapter.count
        fragmentContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(pos: Int) {
                currentFragment = pos
            }
        })

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            ++currentFragment
            if (currentFragment < welcomePagerAdapter.count) {
                fragmentContainer.currentItem = currentFragment
            } else {
                AppConfig.setConfigured(true, this)
                startNavigationActivity()
            }
        }
    }

    override fun onConfigurationChanged() {
        configurationChanged = true
    }

    private fun startNavigationActivity() {
        val intent = Intent(this, NavigationActivity::class.java)
            .putExtra(NavigationActivity.EXTRA_FRAGMENT_TYPE, NavigationActivity.GRID_FRAGMENT)
        if (configurationChanged) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (configurationChanged) {
            startNavigationActivity()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_CURRENT_FRAGMENT, currentFragment)
        outState?.putBoolean(KEY_THEME_CHANGED, configurationChanged)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragment = savedInstanceState?.getInt(KEY_CURRENT_FRAGMENT) ?: 0
        configurationChanged = savedInstanceState?.getBoolean(KEY_THEME_CHANGED) ?: false
    }

    companion object {
        private const val KEY_CURRENT_FRAGMENT = "current fragment"
        private const val KEY_THEME_CHANGED = "theme changed"

        const val EXTRA_SHOW_ONCE = "org.evgem.android.drachukeugenesapp.ui.activity.welcome.show_once"
    }
}