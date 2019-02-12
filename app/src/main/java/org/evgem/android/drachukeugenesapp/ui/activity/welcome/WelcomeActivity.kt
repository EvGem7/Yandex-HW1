package org.evgem.android.drachukeugenesapp.ui.activity.welcome


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.activity.NavigationActivity
import org.evgem.android.drachukeugenesapp.ui.custom.NonSwipeableViewPager

class WelcomeActivity : AppCompatActivity() {
    companion object {
        private const val KEY_CURRENT_FRAGMENT = "current fragment"
    }
    private var currentFragment = 0
    private val welcomePagerAdapter = WelcomePagerAdapter(supportFragmentManager)

    private lateinit var fragmentContainer: NonSwipeableViewPager
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppConfig.isConfigured(this)) {
            startNavigationActivity()
        }

        setContentView(R.layout.activity_welcome)

        fragmentContainer = findViewById(R.id.welcome_fragment_container)
        fragmentContainer.adapter = welcomePagerAdapter
        fragmentContainer.offscreenPageLimit = welcomePagerAdapter.count

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

    private fun startNavigationActivity() {
        val intent = Intent(this@WelcomeActivity, NavigationActivity::class.java)
        intent.putExtra(NavigationActivity.EXTRA_FRAGMENT_TYPE, NavigationActivity.LAUNCHER_FRAGMENT)
        startActivity(intent)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_CURRENT_FRAGMENT, currentFragment)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        currentFragment = savedInstanceState?.getInt(KEY_CURRENT_FRAGMENT) ?: 0
    }
}