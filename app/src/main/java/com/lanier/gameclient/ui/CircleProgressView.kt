package com.lanier.gameclient.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.lanier.gameclient.R
import kotlin.math.min

/**
 * Created by 幻弦让叶
 * Date 2024/4/5 17:26
 */
class CircleProgressView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    private var mw: Int = 0
    private var mh: Int = 0
    private var radius: Int = 0
    private val arcRectf = RectF()
    private val defMaxValue = 100
    private var progressAngle = 0f
    private var wpx = 30

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = wpx * 1f
        strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = min(width, height) / 2 - wpx / 2
        mw = width
        mh = height
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = context.getColor(R.color.md_theme_inversePrimary)
        canvas.drawCircle(mw / 2f, mh / 2f, radius.toFloat(), paint)
        paint.color = context.getColor(R.color.md_theme_primary)
        if (arcRectf.isEmpty) {
            val centerX = mw / 2 * 1f
            val centerY = mh / 2 * 1f
            val l = centerX - radius
            val t = centerY - radius
            val r = centerX + radius
            val b = centerY + radius
            arcRectf.set(l, t, r, b)
        }
        canvas.drawArc(arcRectf, -90f, progressAngle, false, paint)
    }

    fun progress(value: Int) {
        progressAngle = value / defMaxValue.toFloat() * 360 * 1f
        invalidate()
    }
}