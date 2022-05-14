package com.ishzk.android.work.Repository

import com.ishzk.android.work.Model.Achievement

interface Repository {
    fun insert(item: Achievement)
    fun selectAll(): List<Achievement>
}