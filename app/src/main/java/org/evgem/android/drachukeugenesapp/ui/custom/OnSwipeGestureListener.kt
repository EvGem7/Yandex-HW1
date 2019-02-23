package org.evgem.android.drachukeugenesapp.ui.custom

import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

abstract class OnSwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if (abs(velocityX) < VELOCITY_THRESHOLD && abs(velocityY) < VELOCITY_THRESHOLD) {
            return false
        }
        return if (abs(velocityX) > abs(velocityY)) {
            if (velocityX > 0) {
                onSwipeRight()
            } else {
                onSwipeLeft()
            }
        } else {
            if (velocityY < 0) {
                onSwipeUp()
            } else {
                onSwipeDown()
            }
        }
    }

    open fun onSwipeLeft(): Boolean {
        return false
    }

    open fun onSwipeRight(): Boolean {
        return false
    }

    open fun onSwipeUp(): Boolean {
        return false
    }

    open fun onSwipeDown(): Boolean {
        return false
    }

    companion object {
        private const val VELOCITY_THRESHOLD = 1000
    }
}