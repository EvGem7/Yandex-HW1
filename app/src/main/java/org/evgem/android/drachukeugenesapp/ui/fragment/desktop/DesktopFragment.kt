package org.evgem.android.drachukeugenesapp.ui.fragment.desktop

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.ContactRepository
import org.evgem.android.drachukeugenesapp.data.DesktopRepository
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.observer.backimage.BackgroundImageObservable
import org.evgem.android.drachukeugenesapp.data.observer.backimage.BackgroundImageObserver
import org.evgem.android.drachukeugenesapp.ui.custom.OffsetItemDecoration
import org.evgem.android.drachukeugenesapp.util.TAG
import org.evgem.android.drachukeugenesapp.util.getSpanCount
import java.net.URL

class DesktopFragment : Fragment(), BackgroundImageObserver {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: DesktopAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_desktop, container, false)

        recycler = view.findViewById(R.id.desktop_recycler)
        val spanCount = getSpanCount(context)
        recycler.layoutManager = GridLayoutManager(context, spanCount)

        adapter = DesktopAdapter(activity as AppCompatActivity?)
        recycler.adapter = adapter

        val offset = resources.getDimensionPixelOffset(R.dimen.recycler_decoration_offset)
        recycler.addItemDecoration(OffsetItemDecoration(offset))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BackgroundImageObservable.addObserver(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        BackgroundImageObservable.removeObserver(this)
    }

    override fun onResume() {
        super.onResume()
        DesktopRepository.notifyAdapter = { changedItems ->
            for (item in changedItems) {
                adapter.notifyItemChanged(item)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        DesktopRepository.notifyAdapter = null
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
                    adapter.pendingViewHolder?.let { vh ->

                        Log.d(TAG, "onActivityResult: adapter position: ${vh.adapterPosition}")
                        DesktopRepository[vh.adapterPosition] = appEntity
                        adapter.notifyItemChanged(vh.adapterPosition)

                        adapter.pendingViewHolder = null
                    }
                }
            }
        }
    }

    override fun onBackgroundImageObtained(image: Drawable) {
        view?.background = image
    }

    override val name: BackgroundImageObservable.Names get() = BackgroundImageObservable.Names.DESKTOP

    companion object {
        const val CONTACT_REQUEST_CODE = 1234
    }
}