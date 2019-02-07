package org.evgem.android.drachukeugenesapp.ui.custom

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonSwipeableViewPager(context: Context, attributeSet: AttributeSet? = null) : ViewPager(context, attributeSet) {
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}