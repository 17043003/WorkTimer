package com.ishzk.android.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.work.Model.Achievement
import com.ishzk.android.work.Repository.RealmRepository
import java.util.*

class AchievementsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achieve_list)

        val recyclerView: RecyclerView = findViewById(R.id.achievement_list)
        val layout = LinearLayoutManager(this@AchievementsActivity)
        recyclerView.layoutManager = layout

        val list: List<Achievement> = RealmRepository.selectAll()
        val items: MutableList<MutableMap<String, Any>> = list.map { mutableMapOf<String, Any>("detail" to it.description, "time" to it.actualTime) }.toMutableList()
        val adapter = AchievementListAdapter(items)
        recyclerView.adapter = adapter

        val decorator = DividerItemDecoration(this@AchievementsActivity, layout.orientation)
        recyclerView.addItemDecoration(decorator)
    }

    private inner class AchievementListAdapter(private val listData: MutableList<MutableMap<String, Any>>): RecyclerView.Adapter<AchieveViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveViewHolder {
            val inflater = LayoutInflater.from(this@AchievementsActivity)
            val view = inflater.inflate(R.layout.row, parent, false)
            return AchieveViewHolder(view)
        }

        override fun onBindViewHolder(holder: AchieveViewHolder, position: Int) {
            val item = listData[position]
            val detail = item["detail"] as String
            val timeNum = item["time"] as Long
            val timeString = Date(timeNum).toString()

            holder.detailView.text = detail
            holder.timeView.text = timeString
        }

        override fun getItemCount(): Int {
            return listData.size
        }
    }
}

data class RowData(private val detail: String, private val time: Long)

class AchieveViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val detailView: TextView = view.findViewById(R.id.detail)
    val timeView: TextView = view.findViewById(R.id.time)

}