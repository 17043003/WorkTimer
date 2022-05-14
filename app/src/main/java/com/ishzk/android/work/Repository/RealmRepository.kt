package com.ishzk.android.work.Repository

import com.ishzk.android.work.Model.Achievement
import io.realm.Realm
import io.realm.RealmConfiguration

object RealmRepository: Repository {
    private val connection by lazy { connect() }
    fun connect(): Realm{
        val config = RealmConfiguration.Builder(schema = setOf(Achievement::class))
            .build()
        return Realm.open(config)
    }

    override fun insert(item: Achievement) {
        connection.writeBlocking {
            copyToRealm(item)
        }
    }
}