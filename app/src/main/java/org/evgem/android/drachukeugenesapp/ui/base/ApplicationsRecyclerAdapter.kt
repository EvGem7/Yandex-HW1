package org.evgem.android.drachukeugenesapp.ui.base

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.SparseArray
import android.view.View
import android.view.ViewTreeObserver
import org.evgem.android.drachukeugenesapp.entity.Application

abstract class ApplicationsRecyclerAdapter(private val configurationHasher: BaseConfigurationHasher) : Adapter<ApplicationRecyclerViewHolder>() {
    private val appInfoMap = mutableMapOf<String, ApplicationInfo>()
    private var appList: MutableList<Application> = ArrayList()

    private lateinit var context: Context

    private var initialized = false

    private fun onItemViewMeasured(size: Int) {
        if (initialized) {
            return
        }
        val packageManager = context.packageManager
        val resources = context.resources
        for (app in appList) {
            var icon = appInfoMap[app.name]?.loadIcon(packageManager)

            val bitmapIcon = icon as? BitmapDrawable
            bitmapIcon?.let {
                icon = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmapIcon.bitmap, size, size, true))
            }
            app.icon = icon
        }
        notifyDataSetChanged()
        initialized = true
        savedAppLists.put(configurationHasher.hash, appList)
    }

    private fun checkIfAppListLoaded(): Boolean {
        savedAppLists.get(configurationHasher.hash)?.let {
            appList = it
            initialized = true
        }
        return initialized
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        context = recyclerView.context
        if (checkIfAppListLoaded()) {
            return
        }
        val packageManager = context.packageManager

        val applicationInfoList = packageManager.getInstalledApplications(0)
        for (appInfo in applicationInfoList) {
            val launchIntent = packageManager.getLaunchIntentForPackage(appInfo.packageName)
                ?: continue //skip all apps we cannot launch

            val name = appInfo.loadLabel(packageManager).toString()
            appInfoMap[name] = appInfo
            appList.add(Application(null, name, launchIntent))
        }
    }

    final override fun getItemCount(): Int = appList.size

    final override fun onBindViewHolder(holder: ApplicationRecyclerViewHolder, pos: Int) {
        holder.bind(appList[pos])

        if (initialized) {
            return
        }
        holder.itemView.apply {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    onItemViewMeasured(getIconSize(this@apply))
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    protected abstract fun getIconSize(itemView: View): Int

    companion object {
        private val savedAppLists: SparseArray<MutableList<Application>> = SparseArray()
    }
}