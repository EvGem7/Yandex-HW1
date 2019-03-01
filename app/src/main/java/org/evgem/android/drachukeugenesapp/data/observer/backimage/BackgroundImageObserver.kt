package org.evgem.android.drachukeugenesapp.data.observer.backimage

import android.graphics.drawable.Drawable

interface BackgroundImageObserver {
    fun onBackgroundImageObtained(image: Drawable)

    val name: BackgroundImageObservable.Names
}