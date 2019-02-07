package org.evgem.android.drachukeugenesapp.ui.activity.welcome


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.activity.LauncherActivity

class WelcomeActivity : AppCompatActivity() {
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
}