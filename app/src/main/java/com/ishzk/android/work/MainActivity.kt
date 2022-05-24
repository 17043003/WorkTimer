package com.ishzk.android.work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import com.ishzk.android.work.Model.Achievement
import io.realm.Realm
import io.realm.RealmConfiguration

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val hourPicker: NumberPicker by lazy { findViewById(R.id.hourPicker) }
    private val minutePicker: NumberPicker by lazy { findViewById(R.id.minutePicker) }
    private val categorySpin: Spinner by lazy { findViewById(R.id.categorySpin) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerStartButton: Button = findViewById(R.id.startTimerButton)
        timerStartButton.setOnClickListener{
            val (hours, minutes) = getWorkingTime()
            val purpose = getPurpose()

            val category = categorySpin.selectedItem.toString()
            val i = Intent(application, TimerActivity::class.java)
            i.putExtra("hours", hours)
            i.putExtra("minutes", minutes)
            i.putExtra("category", category)
            i.putExtra("purpose", purpose)
            startActivity(i)
        }

        initNumberPicker()

        categorySpin.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item).apply {
            add("Study")
            add("Practice")
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_results -> {
                startActivity(Intent(application, AchievementsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getWorkingTime(): Pair<Int, Int>{
        val hours = hourPicker.value
        val minutes = minutePicker.value
        Log.v(TAG, "hours:$hours")
        Log.v(TAG, "minutes:$minutes")

        return hours to minutes
    }

    private fun getPurpose(): String {
        val purposeText: TextView = findViewById(R.id.purposeText)
        return purposeText.text.toString()
    }

    private fun initNumberPicker(){
        val hourPicker: NumberPicker = findViewById(R.id.hourPicker)
        hourPicker.apply {
            maxValue = 99
            minValue = 0
            value = 0
        }
        val minutePicker: NumberPicker = findViewById(R.id.minutePicker)
        minutePicker.apply {
            maxValue = 59
            minValue = 0
            value = 0
        }
    }
}