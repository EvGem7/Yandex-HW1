package org.evgem.android.drachukeugenesapp.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.TextView
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.activity.welcome.WelcomeActivity
import org.evgem.android.drachukeugenesapp.ui.custom.CircularImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        githubLinkTextView = findViewById(R.id.github_link_text_view)
        githubLinkTextView.text = if (Build.VERSION.SDK_INT < 24) {
            Html.fromHtml(htmlGithubLink)
        } else {
            Html.fromHtml(htmlGithubLink, Html.FROM_HTML_MODE_LEGACY)
        }
        githubLinkTextView.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(githubLink)
            )
            packageManager?.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)?.let {
                startActivity(intent)
            }
        }

        myHeadImageView = findViewById(R.id.my_head_image)
        myHeadImageView.setOnLongClickListener {
            AppConfig.setConfigured(false, this)
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }

    }

    private val githubLink: String get() = getString(R.string.github_link)
    private val htmlGithubLink: String get() = getString(R.string.html_github_link, githubLink)

    private lateinit var githubLinkTextView: TextView
    private lateinit var myHeadImageView: CircularImageView
}
