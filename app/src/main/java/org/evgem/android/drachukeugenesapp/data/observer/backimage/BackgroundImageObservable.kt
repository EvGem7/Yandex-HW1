package org.evgem.android.drachukeugenesapp.data.observer.backimage

import android.app.Application
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import org.evgem.android.drachukeugenesapp.AppConfig
import org.evgem.android.drachukeugenesapp.util.TAG
import org.evgem.android.drachukeugenesapp.util.toBitmap
import java.io.File
import java.io.IOException
import java.util.*

object BackgroundImageObservable {
    private val observers = mutableListOf<BackgroundImageObserver>()
    private lateinit var application: Application
    private val configuration: Configuration get() = application.resources.configuration
    private const val IMAGE_FILE_NAME = "image_%s.png"
    private val restoreFromCacheAsyncTasks = mutableListOf<RestoreFromCacheAsyncTask>()

    enum class Names(val value: String) {
        GRID("grid"), LIST("list"), DESKTOP("desktop")
    }

    fun init(application: Application) {
        this.application = application
    }

    fun onBackgroundImageObtained(portraitImages: MutableList<Drawable>, landscapeImages: MutableList<Drawable>) {
        Log.d(TAG, "onBackgroundImageObtained(). portraitImages.size = ${portraitImages.size}. landscapeImages.size = ${landscapeImages.size}.\nobservers: $observers")

        val isBackgroundImageUnique = AppConfig.isBackgroundImageUnique(application)
        if (!isBackgroundImageUnique) {
            val random = Random()
            portraitImages.fill(portraitImages[random.nextInt(portraitImages.size)])
            landscapeImages.fill(landscapeImages[random.nextInt(landscapeImages.size)])
        }

        val images = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            portraitImages
        } else {
            landscapeImages
        }
        val imagesMap = mutableMapOf<String, Drawable>()
        if (observers.size > images.size) {
            for ((i, observer) in observers.withIndex()) {
                val image = images[i % images.size]
                observer.onBackgroundImageObtained(image)
                imagesMap["${observer.name.value}_portrait"] = portraitImages[i % portraitImages.size]
                imagesMap["${observer.name.value}_landscape"] = landscapeImages[i % landscapeImages.size]
            }
        } else {
            val names = Names.values()
            val notifiedNames = mutableListOf<Names>()
            for ((i, image) in images.withIndex()) {
                if (i < observers.size) {
                    observers[i].onBackgroundImageObtained(image)
                    imagesMap["${observers[i].name.value}_portrait"] = portraitImages[i % portraitImages.size]
                    imagesMap["${observers[i].name.value}_landscape"] = landscapeImages[i % landscapeImages.size]
                    notifiedNames.add(observers[i].name)
                } else {
                    for (name in names) {
                        if (name !in notifiedNames) {
                            imagesMap["${name.value}_portrait"] = portraitImages[i % portraitImages.size]
                            imagesMap["${name.value}_landscape"] = landscapeImages[i % landscapeImages.size]
                            notifiedNames.add(name)
                            break
                        }
                    }
                }
            }
        }
        synchronized(restoreFromCacheAsyncTasks) {
            restoreFromCacheAsyncTasks.forEach { it.cancel(true) }
            restoreFromCacheAsyncTasks.clear()
        }
        SaveToCacheAsyncTask(imagesMap).execute()
    }

    fun addObserver(observer: BackgroundImageObserver) {
        observers.add(observer)
        Log.d(TAG, "addObserver(). observers: $observers")
        val conf = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) "portrait" else "landscape"
        RestoreFromCacheAsyncTask("${observer.name.value}_$conf", observer).execute()
    }

    fun removeObserver(observer: BackgroundImageObserver) {
        observers.remove(observer)
        Log.d(TAG, "removeObserver(). observers: $observers")
    }

    private class SaveToCacheAsyncTask(private val imagesMap: Map<String, Drawable>) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            Log.d(TAG, "doInBackground()")
            try {
                for ((name, image) in imagesMap) {
                    File(
                        application.cacheDir,
                        IMAGE_FILE_NAME.format(name)
                    ).outputStream().use { fileOutputStream ->
                        image.toBitmap().compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream)
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, Log.getStackTraceString(e))
            }
        }
    }

    private class RestoreFromCacheAsyncTask(
        private val name: String,
        private val observer: BackgroundImageObserver
    ) : AsyncTask<Unit, Unit, Drawable?>() {
        override fun onPreExecute() {
            synchronized(restoreFromCacheAsyncTasks) {
                for (task in restoreFromCacheAsyncTasks) {
                    task.cancel(true)
                }
                restoreFromCacheAsyncTasks.clear()
                restoreFromCacheAsyncTasks.add(this)
            }
        }

        override fun doInBackground(vararg params: Unit?): Drawable? = try {
            Log.d(TAG, "doInBackground()")
            val file = File(application.cacheDir, IMAGE_FILE_NAME.format(name))
            BitmapDrawable(application.resources, file.inputStream())
        } catch (e: IOException) {
            Log.e(TAG, Log.getStackTraceString(e))
            null
        }

        override fun onPostExecute(result: Drawable?) {
            Log.d(TAG, "onPostExecute()")
            result?.let { observer.onBackgroundImageObtained(it) }
        }
    }
}