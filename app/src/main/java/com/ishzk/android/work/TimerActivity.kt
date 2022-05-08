package com.ishzk.android.work

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "TimerActivity"

class TimerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val datas = intent.extras
        val hours: Int = datas?.get("hours").toString().toInt()
        val minutes: Int = datas?.get("minutes").toString().toInt()
        Log.v(TAG, "$hours:$minutes")
    }
}