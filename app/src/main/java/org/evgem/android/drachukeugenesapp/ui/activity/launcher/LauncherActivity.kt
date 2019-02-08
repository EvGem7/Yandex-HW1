package org.evgem.android.drachukeugenesapp.ui.activity.launcher

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration

class LauncherActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter: LauncherRecyclerAdapter = LauncherRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        recyclerView = findViewById(R.id.recycler)
        val spanCount = if (resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AppConfig.layout.portraitIconAmount
        } else {
            AppConfig.layout.landscapeIconAmount
        }
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(OffsetItemDecoration(
            resources?.getDimensionPixelOffset(R.dimen.recycler_decoration_offset) ?: 0
        ))
    }
}
