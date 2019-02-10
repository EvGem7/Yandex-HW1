package org.evgem.android.drachukeugenesapp.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class OffsetItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(offset, offset, offset, offset)
    }
}