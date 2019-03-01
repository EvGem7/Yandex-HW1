package org.evgem.android.drachukeugenesapp.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.data.network.LoadBackgroundImageAsyncTask
import org.evgem.android.drachukeugenesapp.util.TAG

class ImageLoaderJobService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob()")
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        startJob(applicationContext)
        jobFinished(params, false)
        return true
    }

    companion object {
        fun startJob(applicationContext: Context) {
            Log.d(TAG, "onStartJob()")
            val (portraitUrls, landscapeUrls) = AppConfig.getBackgroundImageUrls(applicationContext)
            LoadBackgroundImageAsyncTask(applicationContext.resources, portraitUrls, landscapeUrls).execute()
        }
    }
}