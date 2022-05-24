package com.ishzk.android.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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

        val list: MutableList<Achievement> = RealmRepository.selectAll().toMutableList()
        val adapter = AchievementListAdapter(list)
        recyclerView.adapter = adapter

        val decorator = DividerItemDecoration(this@AchievementsActivity, layout.orientation)
        recyclerView.addItemDecoration(decorator)
    }

    private inner class AchievementListAdapter(private val listData: MutableList<Achievement>): RecyclerView.Adapter<AchieveViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveViewHolder {
            val inflater = LayoutInflater.from(this@AchievementsActivity)
            val view = inflater.inflate(R.layout.row, parent, false)
            return AchieveViewHolder(view)
        }

        override fun onBindViewHolder(holder: AchieveViewHolder, position: Int) {
            val item = listData[position]
            val detail = item.description
            val timeNum = item.actualTime / 1000
            val timeString = "${timeNum / 3600}hours, ${(timeNum % 3600) / 60 }minutes."

            holder.detailView.text = detail
            holder.timeView.text = timeString
            holder.itemView.setOnClickListener(ItemClickListener(listData[position]))
        }

        override fun getItemCount(): Int {
            return listData.size
        }
    }

    private inner class ItemClickListener(private val data: Achievement): View.OnClickListener {
        override fun onClick(view: View) {
            Toast.makeText(this@AchievementsActivity, "${data.id}:${data.description}", Toast.LENGTH_SHORT).show()
        }
    }
}

class AchieveViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val detailView: TextView = view.findViewById(R.id.detail)
    val timeView: TextView = view.findViewById(R.id.time)

}