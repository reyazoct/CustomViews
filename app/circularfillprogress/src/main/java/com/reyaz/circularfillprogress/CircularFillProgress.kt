package com.reyaz.circularfillprogress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.acos

class CircularFillProgress(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {
    private val paint = Paint()
    private val borderColor: Int
    private val borderWidth: Float
    private val progress: Int
    private val fillColor: Int
    private val emptyColor: Int

    init {
        val attrs =
            context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.CircularFillProgress,
                0,
                0
            )
        borderColor = attrs.getColor(R.styleable.CircularFillProgress_borderColor, Color.WHITE)
        borderWidth = attrs.getDimension(R.styleable.CircularFillProgress_borderWidth, 0f)
        progress = attrs.getInteger(R.styleable.CircularFillProgress_progress, 70)
        fillColor = attrs.getColor(R.styleable.CircularFillProgress_fillColor, Color.WHITE)
        emptyColor = attrs.getColor(R.styleable.CircularFillProgress_emptyColor, Color.TRANSPARENT)
    }

    override fun onDraw(canvas: Canvas?) {
        val diameter = (if (width > height) height else width).toFloat()
        drawBackground(canvas, diameter)
        drawBorder(canvas, diameter)
        drawProgress(diameter, canvas)
    }

    private fun drawProgress(diameter: Float, canvas: Canvas?) {
        val progressHeight = diameter / 50 * (if (progress > 50) 100 - progress else progress)
        val angleInRadian = acos((diameter / 2 - progressHeight) / (diameter / 2))
        val angleInDegree = if (progress > 50) 360 - Math.toDegrees(angleInRadian.toDouble())
        else Math.toDegrees(angleInRadian.toDouble())

        val startAngle = 90f - (angleInDegree / 2).toFloat()

        paint.style = Paint.Style.FILL
        paint.color = fillColor
        canvas?.drawArc(
            (width - diameter) / 2 + borderWidth,
            (height - diameter) / 2 + borderWidth,
            (width + diameter) / 2 - borderWidth,
            (height + diameter) / 2 - borderWidth,
            startAngle,
            angleInDegree.toFloat(),
            false,
            paint
        )
    }

    private fun drawBorder(canvas: Canvas?, diameter: Float) {
        paint.color = borderColor
        paint.strokeWidth = borderWidth
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (diameter - borderWidth) / 2,
            paint
        )
    }

    private fun drawBackground(canvas: Canvas?, diameter: Float) {
        paint.color = emptyColor
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (diameter - borderWidth) / 2,
            paint
        )
    }
}