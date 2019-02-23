package org.evgem.android.drachukeugenesapp.ui.fragment.grid

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.metrica.YandexMetrica
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.ContactRepository
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationsRecyclerAdapter
import org.evgem.android.drachukeugenesapp.ui.base.BaseLauncherFragment
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter.FavouriteRecyclerAdapter
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter.GridRecyclerAdapter
import org.evgem.android.drachukeugenesapp.util.ReportEvents

class GridLauncherFragment : BaseLauncherFragment() {
    private lateinit var gridRecyclerView: RecyclerView
    private lateinit var favouriteRecyclerView: RecyclerView

    private lateinit var gridLayoutManager: GridLayoutManager
    override val adapter: GridRecyclerAdapter = GridRecyclerAdapter()
    private lateinit var favouriteAdapter: FavouriteRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationObservable.addObserver(this)
        if (savedInstanceState == null) {
            YandexMetrica.reportEvent(ReportEvents.GRID_FRAGMENT_STARTED)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationObservable.removeObserver(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_grid, container, false)

        //setup grid recycler
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        gridRecyclerView = view.findViewById(R.id.grid_recycler)

        val spanCount = if (resources.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AppConfig.getLayout(context).portraitIconAmount
        } else {
            AppConfig.getLayout(context).landscapeIconAmount
        }
        gridLayoutManager = GridLayoutManager(context, spanCount)
        gridRecyclerView.layoutManager = gridLayoutManager

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val position = gridLayoutManager.findFirstCompletelyVisibleItemPosition()
                gridLayoutManager.scrollToPosition(position)
            }
        })
        gridRecyclerView.adapter = adapter

        val offset = resources.getDimensionPixelOffset(R.dimen.recycler_decoration_offset)
        val offsetItemDecoration = OffsetItemDecoration(offset)
        gridRecyclerView.addItemDecoration(offsetItemDecoration)
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        favouriteRecyclerView = view.findViewById(R.id.favourite_recycler)
        if (!AppConfig.isFavouriteShown(context)) {
            favouriteRecyclerView.visibility = View.GONE
        }

        favouriteRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        favouriteRecyclerView.addItemDecoration(offsetItemDecoration)

        favouriteAdapter = FavouriteRecyclerAdapter(spanCount, activity as AppCompatActivity)
        FavouriteRepository.favouriteAdapter = favouriteAdapter
        favouriteRecyclerView.adapter = favouriteAdapter


        adapter.onFavouriteAdd = {
            favouriteAdapter.currentAddingFavourite = it
            favouriteAdapter.newFavouriteAdding = true
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FavouriteRepository.favouriteAdapter = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            CONTACT_REQUEST_CODE -> {
                data?.data?.let { contactUri ->
                    val appEntity =
                        FavouriteRepository.getAppEntityFromContact(ContactRepository.getContact(contactUri) ?: return)
                    favouriteAdapter.pendingActivityResultHolder?.let {
                        it.bind(appEntity)
                        FavouriteRepository[it.adapterPosition] = appEntity
                    }
                    favouriteAdapter.pendingActivityResultHolder = null
                }
            }
        }
    }

    companion object {
        const val CONTACT_REQUEST_CODE = 1000
    }
}
