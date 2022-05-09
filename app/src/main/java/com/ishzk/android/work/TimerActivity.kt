package com.ishzk.android.work

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "TimerActivity"

class TimerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val canvasView = TimerCanvasView(applicationContext)
        setContentView(canvasView)

        val datas = intent.extras
        val hours: Int = datas?.get("hours").toString().toInt()
        val minutes: Int = datas?.get("minutes").toString().toInt()
        Log.v(TAG, "$hours:$minutes")

        val animation = AnimationDraw(canvasView)
        animation.duration = (hours * 60 + minutes) * 60 * 1000L
        canvasView.startAnimation(animation)
    }

    class TimerCanvasView(context: Context): View(context){
        private val paint: Paint by lazy { Paint() }
        var arcRate: Float = 0.0f

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            val r = 3 * width / 4f
            val x = width / 8f
            val y = height / 4f
            val rect = RectF(x, y, x + r, y + r)
            drawBaseArc(canvas, rect)
            drawTimerArc(canvas, rect, arcRate)
        }

        private fun drawBaseArc(canvas: Canvas?, rectF: RectF){
            paint.color = Color.argb(30, 255, 0, 255)
            paint.strokeWidth = 220f
            paint.style = Paint.Style.STROKE
            paint.isAntiAlias = true
            canvas?.drawArc(rectF, 0f, 360f, false, paint)
        }

        private fun drawTimerArc(canvas: Canvas?, rectF: RectF, rate: Float){
            paint.color = Color.argb(255, 255, 0, 255)
            paint.strokeWidth = 220f
            paint.style = Paint.Style.STROKE
            paint.isAntiAlias = true
            canvas?.drawArc(rectF, 0f, rate * 360f, false, paint)
            Log.v(TAG, "Rate is $rate")
        }
    }
}

class AnimationDraw: Animation {
    private var timerView: TimerActivity.TimerCanvasView
    constructor(view: TimerActivity.TimerCanvasView){
        timerView = view
    }
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)

        timerView.arcRate = interpolatedTime
        timerView.requestLayout()
    }
}