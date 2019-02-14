package org.evgem.android.drachukeugenesapp.ui.fragment.launcher

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.configuration.LauncherConfigurationHasher
import org.evgem.android.drachukeugenesapp.ui.activity.NavigationActivity
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration

class LauncherFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
//    private lateinit var fab: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_launcher, container, false)

        setupRecycler(view)

//        fab = view.findViewById(R.id.launcher_fab)
//        fab.setOnClickListener {
//            adapter.insert(0)
//            recyclerView.scrollToPosition(0)
//        }

        return view
    }

    private fun setupRecycler(view: View) {
        recyclerView = view.findViewById(R.id.recycler)

        val spanCount = if (resources.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AppConfig.getLayout(context).portraitIconAmount
        } else {
            AppConfig.getLayout(context).landscapeIconAmount
        }
        recyclerView.layoutManager = GridLayoutManager(context, spanCount)

        recyclerView.adapter =
                LauncherRecyclerAdapter(
                    LauncherConfigurationHasher(
                        context,
                        NavigationActivity.LAUNCHER_FRAGMENT
                    )
                )

        val offset = resources.getDimensionPixelOffset(R.dimen.recycler_decoration_offset)
        recyclerView.addItemDecoration(OffsetItemDecoration(offset))

        recyclerView.itemAnimator = LauncherRecyclerItemAnimator(offset)
    }
}
