package org.evgem.android.drachukeugenesapp.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import org.evgem.android.drachukeugenesapp.R
import java.lang.Math.pow
import java.lang.Math.sqrt

//this code is stolen
//TODO разобраться
class CircularImageView(context: Context, attributeSet: AttributeSet? = null) : ImageView(context, attributeSet) {
    private var bitmapShader: BitmapShader? = null
    private val shaderMatrix: Matrix = Matrix()

    private val bitmapDrawBounds = RectF()
    private val strokeDrawBounds = RectF()

    private var bitmap: Bitmap? = null

    private val bitmapPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var initialized: Boolean

    init {
        var strokeColor = Color.TRANSPARENT
        var strokeWidth = 0f

        if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircularImageView)

            strokeColor = typedArray.getColor(R.styleable.CircularImageView_stroke_color, Color.TRANSPARENT)
            strokeWidth = typedArray.getDimension(R.styleable.CircularImageView_stroke_width, 0f)


            typedArray.recycle()
        }

        strokePaint.color = strokeColor
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = strokeWidth

        initialized = true
        setupBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        setupBitmap()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        setupBitmap()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        setupBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        setupBitmap()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (isInCircle(event?.x, event?.y)) {
            super.onTouchEvent(event)
        } else {
            false
        }
    }

    private fun isInCircle(x: Float?, y: Float?): Boolean {
        if (x == null || y == null) {
            return false
        }
        val distance = sqrt(
            pow((bitmapDrawBounds.centerX() - x).toDouble(), 2.0) + pow((bitmapDrawBounds.centerY() - y).toDouble(), 2.0)
        )
        return distance <= bitmapDrawBounds.width() / 2
    }

    override fun onDraw(canvas: Canvas?) {
        drawBitmap(canvas)
        drawStroke(canvas)
    }

    private fun drawStroke(canvas: Canvas?) {
        if (strokePaint.strokeWidth > 0f) {
            canvas?.drawOval(strokeDrawBounds, strokePaint)
        }
    }

    private fun drawBitmap(canvas: Canvas?) {
        canvas?.drawOval(bitmapDrawBounds, bitmapPaint)
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)

        return bitmap
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val halfStrokeWidth = strokePaint.strokeWidth / 2f
        updateCircleDrawBounds()
        strokeDrawBounds.set(bitmapDrawBounds)
        strokeDrawBounds.inset(halfStrokeWidth, halfStrokeWidth)

        updateBitmapSize()
    }

    private fun updateCircleDrawBounds() {
        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingBottom - paddingTop

        var left: Float = paddingLeft.toFloat()
        var top: Float = paddingTop.toFloat()

        if (contentWidth > contentHeight) {
            left += (contentWidth - contentHeight) / 2f
        } else {
            top += (contentHeight - contentWidth) / 2f
        }

        val diameter = Math.min(contentWidth, contentHeight)
        bitmapDrawBounds.set(left, top, left + diameter, top + diameter)
    }

    private fun setupBitmap() {
        if (!initialized) {
            return
        }
        bitmap = getBitmapFromDrawable(drawable) ?: return
        bitmapShader = BitmapShader(bitmap ?: return, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapPaint.shader = bitmapShader

        updateBitmapSize()
    }

    private fun updateBitmapSize() {
        // trainslate bitmap in the BitmapShader using dx and dy so that it's centered
        val dx: Float
        val dy: Float

        // scale factor
        val scale: Float

        // scale up/down with respect to this view size and maintain aspect ratio
        // translate bitmap position with dx/dy to the center of the image
        //
        // check do we want to scale based on width or height
        if (bitmap?.width ?: return < bitmap?.height ?: return) {
            // if bitmp with is less than its height, we wanna scale based on its width
            // assign scale factor based on difference (ratio) between bitmap width and bitmap draw bounds
            scale = bitmapDrawBounds.width() / (bitmap?.width?.toFloat() ?: return)
            // because we know that the scale was based on width, the width would fit
            // exaclty with bounds, so we just translate x with its left padding
            dx = bitmapDrawBounds.left
            // we want to center y(height) axis of the scaled bitmap,
            // the logial way to see this is:
            // at the first state bitmap would rendered at the top left area
            // by translating with top padding of the view,
            // translate up by half of bitmap height (so center of bitmap now in the top of the view),
            // translate down by half of the bitmap bounds (so center of bitmap would be in the center of the view (bitmap bounds))
            dy = bitmapDrawBounds.top - ((bitmap?.height ?: return) * scale / 2f) + (bitmapDrawBounds.width() / 2f)
        } else {
            // the same concept goes the same here, the difference is we
            // translate (center) horizontal axis instead of vertical/y axis
            scale = bitmapDrawBounds.height() / (bitmap?.width?.toFloat() ?: return)
            dx = bitmapDrawBounds.left - ((bitmap?.width ?: return) * scale / 2f) + (bitmapDrawBounds.width() / 2f)
            dy = bitmapDrawBounds.top
        }

        // apply this transformation into shader matrix -> bitmap shader
        shaderMatrix.setScale(scale, scale)
        shaderMatrix.postTranslate(dx, dy)
        bitmapShader?.setLocalMatrix(shaderMatrix)
    }
}
