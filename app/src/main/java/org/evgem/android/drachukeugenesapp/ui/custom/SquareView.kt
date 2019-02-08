package org.evgem.android.drachukeugenesapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View

class SquareView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}