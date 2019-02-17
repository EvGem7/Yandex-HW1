package org.evgem.android.drachukeugenesapp.ui.base

import android.support.v4.app.Fragment
import org.evgem.android.drachukeugenesapp.data.application.ApplicationObserver

abstract class BaseLauncherFragment : Fragment(), ApplicationObserver {
    protected abstract val adapter: ApplicationsRecyclerAdapter

    final override fun onPackageInstalled(packageName: String?, position: Int) {
        adapter.notifyItemInserted(position)
    }

    final override fun onPackageRemoved(packageName: String?, position: Int) {
        adapter.notifyItemRemoved(position)
    }
}