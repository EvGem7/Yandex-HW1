package org.evgem.android.drachukeugenesapp.ui.base

import android.support.v4.app.Fragment
import org.evgem.android.drachukeugenesapp.data.observer.application.ApplicationObserver
import org.evgem.android.drachukeugenesapp.ui.base.recycler.ApplicationAdapter

abstract class BaseLauncherFragment : Fragment(),
    ApplicationObserver {
    abstract val adapter: ApplicationAdapter

    override fun onSort() {
        adapter.notifyDataSetChanged()
    }

    override fun onPackageInstalled(packageName: String?, position: Int) {
        adapter.notifyItemInserted(position)
    }

    override fun onPackageRemoved(packageName: String?, position: Int) {
        adapter.notifyItemRemoved(position)
    }
}