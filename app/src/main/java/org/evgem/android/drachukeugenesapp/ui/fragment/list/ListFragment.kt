package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.metrica.YandexMetrica
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.ui.base.ApplicationsRecyclerAdapter
import org.evgem.android.drachukeugenesapp.ui.base.BaseLauncherFragment
import org.evgem.android.drachukeugenesapp.ui.custom.DividerItemDecoration
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration
import org.evgem.android.drachukeugenesapp.util.ReportEvents

class ListFragment : BaseLauncherFragment() {
    private lateinit var recyclerView: RecyclerView
    override val adapter: ApplicationsRecyclerAdapter = ListRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationObservable.addObserver(this)
        YandexMetrica.reportEvent(ReportEvents.LIST_FRAGMENT_STARTED)
    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationObservable.removeObserver(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(view.context))

        val offset = context?.resources?.getDimensionPixelOffset(R.dimen.recycler_decoration_offset) ?: 0
        recyclerView.addItemDecoration(OffsetItemDecoration(offset))

        return view
    }
}