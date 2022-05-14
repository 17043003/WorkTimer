package com.ishzk.android.work

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ishzk.android.work.Model.Achievement
import com.ishzk.android.work.Repository.RealmRepository
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TimerActivity"

class TimerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_timer)

        val datas = intent.extras
        val hours: Int = datas?.get("hours").toString().toInt()
        val minutes: Int = datas?.get("minutes").toString().toInt()
        val leftTimeView: TextView = findViewById(R.id.leftTimeText)
        Log.v(TAG, "$hours:$minutes")

        val purposeTextView: TextView = findViewById(R.id.purposeDisplay)
        val purpose = datas?.get("purpose").toString()
        purposeTextView.text = purpose

        val currentTimeView: TextView = findViewById(R.id.currentTimeText)

        val canvasView: TimerCanvasView = findViewById(R.id.canvasView)
        val animation = AnimationDraw(canvasView, leftTimeView, currentTimeView)
        animation.duration = (hours * 60 + minutes) * 60 * 1000L
        canvasView.startAnimation(animation)

        val endTimeText: TextView = findViewById(R.id.endTimeText)
        val endTime = Date().apply { this.time += animation.duration }
        endTimeText.text = SimpleDateFormat(" / HH:mm:ss").format(endTime)

        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val dbConnection = RealmRepository
            dbConnection.insert(Achievement().apply {
                id = 1
                category = datas?.get("category").toString()
                description = purpose
                actualTime = Date().time.apply { hours + minutes }
                scheduledTime = endTime.time
            })
            Log.v(TAG, "saved Achievement!")
        }
    }

    class TimerCanvasView: View{
        private val paint: Paint by lazy { Paint() }
        var arcRate: Float = 0.0f

        constructor(context: Context): super(context)
        constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
        constructor(context: Context, attributeSet: AttributeSet, defStyle: Int): super(context, attributeSet, defStyle)

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

class AnimationDraw(
    private val timerView: TimerActivity.TimerCanvasView,
    private val leftTextView: TextView,
    private val currentTimeView: TextView,
): Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)

        val currentHourMinuteSecond = SimpleDateFormat("HH:mm:ss").format(Date())
        currentTimeView.text = currentHourMinuteSecond

        val leftSeconds = (duration -  interpolatedTime * duration).toInt() / 1000
        leftTextView.text = "Left Time : ${leftSeconds / 60} : ${leftSeconds % 60}"
        timerView.arcRate = interpolatedTime
        timerView.requestLayout()
    }
}