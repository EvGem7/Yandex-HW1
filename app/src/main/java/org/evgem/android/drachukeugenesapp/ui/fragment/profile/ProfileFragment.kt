package org.evgem.android.drachukeugenesapp.ui.fragment.profile

import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.yandex.metrica.YandexMetrica
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.ui.base.LockableActivity
import org.evgem.android.drachukeugenesapp.ui.custom.DividerItemDecoration
import org.evgem.android.drachukeugenesapp.util.ReportEvents

//TODO add offset between items
//TODO fix bug: when we go to profile fragment from landscape orientation displayMetrics isn't updated
class ProfileFragment : Fragment() {
    private val adapter = ProfileRecyclerAdapter()

    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (activity as? LockableActivity)?.orientationLocked = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            YandexMetrica.reportEvent(ReportEvents.PROFILE_FRAGMENT_STARTED)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        toolbar = view.findViewById(R.id.profile_toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        recycler = view.findViewById(R.id.profile_recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        context?.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))?.apply {
            val divider: Drawable? = getDrawable(0)

            val leftInset = resources.getDimensionPixelOffset(R.dimen.divider_offset)

            val insetDivider = InsetDrawable(divider, leftInset, 0, 0, 0)
            val dividerItemDecoration = DividerItemDecoration(view.context, showLastItem = false)
                .apply {
                    this.divider = insetDivider
                }
            recycler.addItemDecoration(dividerItemDecoration)
            recycle()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        (activity as? LockableActivity)?.orientationLocked = false
    }
}