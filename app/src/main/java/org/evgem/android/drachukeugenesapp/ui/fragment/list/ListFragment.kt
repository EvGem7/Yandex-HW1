package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.configuration.LauncherConfigurationHasher
import org.evgem.android.drachukeugenesapp.ui.activity.NavigationActivity
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration

class ListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
//    private lateinit var fab: FloatingActionButton

    private lateinit var adapter: ListRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        adapter = ListRecyclerAdapter(
            LauncherConfigurationHasher(
                context,
                NavigationActivity.LIST_FRAGMENT
            )
        )

        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val offset = context?.resources?.getDimensionPixelOffset(R.dimen.recycler_decoration_offset) ?: 0
        recyclerView.addItemDecoration(OffsetItemDecoration(offset))

//        fab = view.findViewById(R.id.list_fab)
//        fab.setOnClickListener {
//            adapter.insert(0)
//            recyclerView.smoothScrollToPosition(0)
//        }

        return view
    }
}