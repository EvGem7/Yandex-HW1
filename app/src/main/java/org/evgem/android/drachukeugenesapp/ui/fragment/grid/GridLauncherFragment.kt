package org.evgem.android.drachukeugenesapp.ui.fragment.grid

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationsRecyclerAdapter
import org.evgem.android.drachukeugenesapp.ui.base.BaseLauncherFragment
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration

class GridLauncherFragment : BaseLauncherFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: GridLayoutManager
    override val adapter: ApplicationsRecyclerAdapter = GridRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationObservable.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationObservable.removeObserver(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_grid, container, false)
        setupRecycler(view)
        return view
    }

    private fun setupRecycler(view: View) {
        //TODO implement stop scrolling between items
        recyclerView = view.findViewById(R.id.recycler)

        val spanCount = if (resources.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AppConfig.getLayout(context).portraitIconAmount
        } else {
            AppConfig.getLayout(context).landscapeIconAmount
        }
        layoutManager = GridLayoutManager(context, spanCount)
        recyclerView.layoutManager = layoutManager

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                layoutManager.scrollToPosition(position)
            }
        })
        recyclerView.adapter = adapter

        val offset = resources.getDimensionPixelOffset(R.dimen.recycler_decoration_offset)
        recyclerView.addItemDecoration(OffsetItemDecoration(offset))
    }
}
