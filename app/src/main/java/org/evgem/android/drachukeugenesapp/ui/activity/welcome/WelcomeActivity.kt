package org.evgem.android.drachukeugenesapp.ui.activity.welcome


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.activity.launcher.LauncherActivity

class WelcomeActivity : AppCompatActivity() {
    companion object {
        private const val KEY_CURRENT_FRAGMENT = "current fragment"
    }
    private var currentFragment = 0
    private val welcomePagerAdapter = WelcomePagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        fragment_container?.adapter = welcomePagerAdapter

        next_button?.setOnClickListener {
            ++currentFragment
            if (currentFragment < welcomePagerAdapter.count) {
                fragment_container?.currentItem = currentFragment
            } else {
                startActivity(
                    Intent(this@WelcomeActivity, LauncherActivity::class.java)
                )
            }
        }
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