package com.lanier.gameclient.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
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
    private var maxValue = 100
    private var progressAngle = 0f
    private var text = ""
    private var wpx = 20
    private var centerX = 0f
    private var centerY = 0f
    private val textPaintRect = Rect()

    private val color1 = context.getColor(R.color.md_theme_inversePrimary)
    private val color2 = context.getColor(R.color.md_theme_primary)

    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = 32f
        textAlign = Paint.Align.CENTER
    }

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
        centerX = mw / 2 * 1f
        centerY = mh / 2 * 1f
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = color1
        canvas.drawCircle(centerX, centerY, radius.toFloat(), paint)
        paint.color = color2
        if (arcRectf.isEmpty) {
            val l = centerX - radius
            val t = centerY - radius
            val r = centerX + radius
            val b = centerY + radius
            arcRectf.set(l, t, r, b)
        }
        canvas.drawArc(arcRectf, -90f, progressAngle, false, paint)
        textPaint.color = color2
        textPaint.getTextBounds(text, 0, text.length - 1, textPaintRect)
        canvas.drawText(text, centerX, centerY + textPaintRect.height() / 2f, textPaint)
    }

    fun maxProgress(value: Int) {
        this.maxValue = value
        invalidate()
    }

    fun progress(value: Int, text: String) {
        if (text != this.text) {
            this.text = text
            textPaintRect.setEmpty()
        }
        progressAngle = value / maxValue.toFloat() * 360 * 1f
        invalidate()
    }
}