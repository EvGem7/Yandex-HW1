package org.evgem.android.drachukeugenesapp.data.network

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import org.evgem.android.drachukeugenesapp.data.observer.backimage.BackgroundImageObservable
import org.evgem.android.drachukeugenesapp.util.TAG

class LoadBackgroundImageAsyncTask(
    private val resources: Resources,
    private val portraitUrls: List<String>,
    private val landscapeUrls: List<String>
) : AsyncTask<Unit, Unit, Unit>() {
    private lateinit var portraitImages: MutableList<Drawable>
    private lateinit var landscapeImages: MutableList<Drawable>

    override fun onPreExecute() {
        synchronized(tasks) {
            for (task in tasks) {
                task.cancel(true)
            }
            tasks.clear()
            tasks.add(this)
        }
    }

    override fun doInBackground(vararg params: Unit?) {
        Log.d(TAG, "doInBackground()")
        portraitImages = getImages(portraitUrls)
        landscapeImages = getImages(landscapeUrls)
    }

    private fun getImages(urls: List<String>): MutableList<Drawable> {
        val images = mutableListOf<Drawable>()
        var i = 0
        while (images.size < BackgroundImageObservable.Names.values().size) {
            images.add(loadImage(resources, urls[i++ % urls.size]) ?: continue)
        }
        return images
    }

    override fun onPostExecute(result: Unit) {
        Log.d(TAG, "onPostExecute()")
        BackgroundImageObservable.onBackgroundImageObtained(portraitImages, landscapeImages)
    }

    companion object {
        private val tasks = mutableListOf<LoadBackgroundImageAsyncTask>()
    }
}