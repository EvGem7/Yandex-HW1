package org.evgem.android.drachukeugenesapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.support.v7.widget.RecyclerView.State
import android.util.Log
import android.view.View
import org.evgem.android.drachukeugenesapp.util.TAG

class DividerItemDecoration(
    context: Context,
    orientation: Int = VERTICAL,
    var showLastItem: Boolean = true
) : ItemDecoration() {
    var divider: Drawable? = null
    private val bounds = Rect()
    private var orientation: Int = orientation
        set(value) {
            if (value != VERTICAL && value != HORIZONTAL) {
                throw IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL")
            } else {
                field = orientation
            }
        }

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        if (divider == null) {
            Log.w(
                TAG,
                "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration." +
                        " Please set that attribute all call setDrawable()"
            )
        }

        a.recycle()
        this.orientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: State) {
        if (parent.layoutManager != null && divider != null) {
            if (orientation == VERTICAL) {
                drawVertical(c, parent)
            } else {
                drawHorizontal(c, parent)
            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        val childCount = if (showLastItem) parent.childCount else parent.childCount - 1

        for (i in 0 until childCount) {
            divider?.let {
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, bounds)
                val bottom = bounds.bottom + Math.round(child.translationY)
                val top = bottom - it.intrinsicHeight
                it.setBounds(left, top, right, bottom)
                it.draw(canvas)
            }
        }

        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = if (showLastItem) parent.childCount else parent.childCount - 1

        for (i in 0 until childCount) {
            divider?.let {
                val child = parent.getChildAt(i)
                parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)
                val right = bounds.right + Math.round(child.translationX)
                val left = right - it.intrinsicWidth
                it.setBounds(left, top, right, bottom)
                it.draw(canvas)
            }
        }

        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        divider?.let {
            if (orientation == VERTICAL) {
                outRect.set(0, 0, 0, it.intrinsicHeight)
            } else {
                outRect.set(0, 0, it.intrinsicWidth, 0)
            }
        } ?: outRect.set(0, 0, 0, 0)
    }

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
