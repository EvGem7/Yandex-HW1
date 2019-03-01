package org.evgem.android.drachukeugenesapp.ui.activity.navigation

import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import org.evgem.android.drachukeugenesapp.ui.custom.OnSwipeGestureListener
import org.evgem.android.drachukeugenesapp.util.TAG

class FragmentSwipeController(private val activity: NavigationActivity?) {
    private lateinit var gestureDetector: GestureDetectorCompat

    fun onSetFragment(fragmentType: Int) {
        Log.d(TAG, "onSetFragment")
        val index = fragments.indexOf(fragmentType)
        if (index == -1) {
            return
        }
        gestureDetector = GestureDetectorCompat(activity, getOnGestureDetectorListener(index))

        activity?.fragmentContainer?.onInterceptTouchEventListener = { event ->
            Log.i(TAG, "onTouchListener, event: $event")
            gestureDetector.onTouchEvent(event)
        }
    }

    private fun getOnGestureDetectorListener(index: Int): OnSwipeGestureListener = when (index) {
        0 -> object : OnSwipeGestureListener() {
            override fun onSwipeLeft(): Boolean {
                activity?.setFragment(fragments[index + 1])
                return true
            }
        }

        fragments.lastIndex -> object : OnSwipeGestureListener() {
            override fun onSwipeRight(): Boolean {
                activity?.setFragment(fragments[index - 1])
                return true
            }
        }

        else -> object : OnSwipeGestureListener() {
            override fun onSwipeLeft(): Boolean {
                activity?.setFragment(fragments[index + 1])
                return true
            }

            override fun onSwipeRight(): Boolean {
                activity?.setFragment(fragments[index - 1])
                return true
            }
        }
    }

    companion object {
        private val fragments = listOf(
            NavigationActivity.DESKTOP_FRAGMENT,
            NavigationActivity.GRID_FRAGMENT,
            NavigationActivity.LIST_FRAGMENT
        )
    }
}