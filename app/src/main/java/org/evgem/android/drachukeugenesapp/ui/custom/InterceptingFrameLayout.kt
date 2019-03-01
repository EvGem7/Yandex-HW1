package org.evgem.android.drachukeugenesapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import org.evgem.android.drachukeugenesapp.util.TAG

class InterceptingFrameLayout(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {
    var onInterceptTouchEventListener: ((event: MotionEvent?) -> Boolean)? = null

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return (onInterceptTouchEventListener?.invoke(ev) ?: false) || super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return onInterceptTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }
}