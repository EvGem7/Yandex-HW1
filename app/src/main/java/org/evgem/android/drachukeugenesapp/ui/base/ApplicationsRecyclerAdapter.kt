package org.evgem.android.drachukeugenesapp.ui.base

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewTreeObserver
import org.evgem.android.drachukeugenesapp.entity.Application

abstract class ApplicationsRecyclerAdapter : Adapter<ApplicationRecyclerViewHolder>() {
    private val appInfoMap = mutableMapOf<String, ApplicationInfo>()
    private val appList = ArrayList<Application>()

    private lateinit var context: Context

    private var initialized = false

    private fun onItemViewMeasured(size: Int) {
        val packageManager = context.packageManager
        val resources = context.resources

        if (!initialized) {
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
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        context = recyclerView.context
        val packageManager = context.packageManager

        val applicationInfoList = packageManager.getInstalledApplications(0)
        for (appInfo in applicationInfoList) {
            packageManager.getLaunchIntentForPackage(appInfo.packageName) ?: continue //skip all apps we cannot launch

            val name = appInfo.loadLabel(packageManager).toString()
            appInfoMap[name] = appInfo
            appList.add(Application(null, name))
        }
    }

    final override fun getItemCount(): Int = appList.size

    final override fun onBindViewHolder(holder: ApplicationRecyclerViewHolder, pos: Int) {
        holder.bind(appList[pos].icon, appList[pos].name)

        if (!initialized) {
            holder.itemView.apply {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        onItemViewMeasured(getIconSize(this@apply))
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }
    }

    protected abstract fun getIconSize(itemView: View): Int
}