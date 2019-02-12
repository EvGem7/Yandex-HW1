package org.evgem.android.drachukeugenesapp.ui.fragment.launcher

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.util.OffsetItemDecoration

class LauncherFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LauncherRecyclerAdapter
    private lateinit var fab: FloatingActionButton

    var itemCount: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_launcher, container, false)

        itemCount = savedInstanceState?.getInt(KEY_ITEM_COUNT) ?: itemCount
        adapter = object : LauncherRecyclerAdapter(itemCount) {
            override fun insert(pos: Int, color: Int?) {
                super.insert(pos, color)
                this@LauncherFragment.itemCount = itemCount
            }

            override fun delete(pos: Int) {
                super.delete(pos)
                this@LauncherFragment.itemCount = itemCount
            }
        }

        recyclerView = view.findViewById(R.id.recycler)
        val spanCount = if (resources.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AppConfig.getLayout(context).portraitIconAmount
        } else {
            AppConfig.getLayout(context).landscapeIconAmount
        }
        recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        recyclerView.adapter = adapter

        val offset = resources.getDimensionPixelOffset(R.dimen.recycler_decoration_offset)
        recyclerView.addItemDecoration(OffsetItemDecoration(offset))
        recyclerView.itemAnimator = LauncherRecyclerItemAnimator(offset)

        fab = view.findViewById(R.id.launcher_fab)
        fab.setOnClickListener {
//            val scrollOffset = recyclerView.computeVerticalScrollOffset()
            adapter.insert(0)
            recyclerView.scrollToPosition(0)
//            val layoutManager = recyclerView.layoutManager as GridLayoutManager
//            layoutManager.scrollToPositionWithOffset(0, -scrollOffset)
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_ITEM_COUNT, itemCount)
    }

    companion object {
        private const val KEY_ITEM_COUNT = "item count"
    }
}
