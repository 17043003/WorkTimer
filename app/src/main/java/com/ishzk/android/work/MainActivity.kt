package com.ishzk.android.work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val hourText: TextView by lazy { findViewById(R.id.hourTextNumber) }
    private val minuteText: TextView by lazy { findViewById(R.id.minuteTextNumber) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerStartButton: Button = findViewById(R.id.startTimerButton)
        timerStartButton.setOnClickListener{
            val (hours, minutes) = getWorkingTime()
            val i = Intent(application, TimerActivity::class.java)
            i.putExtra("hours", hours)
            i.putExtra("minutes", minutes)
            startActivity(i)
        }
    }

    private fun getWorkingTime(): Pair<Int, Int>{
        val hours = hourText.text.toString().toInt()
        val minutes = minuteText.text.toString().toInt()
        Log.v(TAG, "hours:$hours")
        Log.v(TAG, "minutes:$minutes")

        return hours to minutes
    }
}