package com.ishzk.android.work.Model

import io.realm.RealmObject

class Achievement: RealmObject {
    var id: Int = 0
    var category: String = ""
    var description: String = ""
    var actualTime: Long = 0L
    var scheduledTime: Long = 0L
}