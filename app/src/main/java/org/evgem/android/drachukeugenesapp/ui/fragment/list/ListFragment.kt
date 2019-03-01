package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.metrica.YandexMetrica
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObservable
import org.evgem.android.drachukeugenesapp.data.observer.backimage.BackgroundImageObservable
import org.evgem.android.drachukeugenesapp.data.observer.backimage.BackgroundImageObserver
import org.evgem.android.drachukeugenesapp.ui.base.recycler.ApplicationAdapter
import org.evgem.android.drachukeugenesapp.ui.base.BaseLauncherFragment
import org.evgem.android.drachukeugenesapp.ui.custom.DividerItemDecoration
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration
import org.evgem.android.drachukeugenesapp.util.ReportEvents

class ListFragment : BaseLauncherFragment(), BackgroundImageObserver {
    private lateinit var recyclerView: RecyclerView
    override val adapter: ApplicationAdapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationObservable.addObserver(this)
        YandexMetrica.reportEvent(ReportEvents.LIST_FRAGMENT_STARTED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        BackgroundImageObservable.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationObservable.removeObserver(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        BackgroundImageObservable.removeObserver(this)
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

    override fun onBackgroundImageObtained(image: Drawable) {
        view?.background = image
    }

    override val name: BackgroundImageObservable.Names get() = BackgroundImageObservable.Names.LIST
}