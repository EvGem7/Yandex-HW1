package org.evgem.android.drachukeugenesapp.ui.activity.launcher

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_launcher.*
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.util.OffsetItemDecoration

class LauncherActivity : AppCompatActivity() {
    companion object {
        private const val KEY_ITEM_COUNT = "item count"
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LauncherRecyclerAdapter

    private var itemCount: Int = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        itemCount = savedInstanceState?.getInt(KEY_ITEM_COUNT) ?: itemCount
        adapter = LauncherRecyclerAdapter(itemCount)

        recyclerView = findViewById(R.id.recycler)
        val spanCount = if (resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AppConfig.layout.portraitIconAmount
        } else {
            AppConfig.layout.landscapeIconAmount
        }
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.adapter = adapter

        val offset = resources?.getDimensionPixelOffset(R.dimen.recycler_decoration_offset) ?: 0
        recyclerView.addItemDecoration(OffsetItemDecoration(offset))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            startActivity(parentActivityIntent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_ITEM_COUNT, itemCount)
    }
}
