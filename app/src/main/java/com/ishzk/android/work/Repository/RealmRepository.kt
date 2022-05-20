package com.ishzk.android.work.Repository

import com.ishzk.android.work.Model.Achievement
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.query

object RealmRepository: Repository {
    private val connection by lazy { connect() }
    private fun connect(): Realm{
        val config = RealmConfiguration.Builder(schema = setOf(Achievement::class))
            .build()
        return Realm.open(config)
    }

    override fun insert(item: Achievement) {
        connection.writeBlocking {
            copyToRealm(item)
        }
    }

    override fun selectAll(): List<Achievement> {
        return connection.query<Achievement>().find()
    }

    override fun select(id: Int): Achievement {
        return connection.query<Achievement>("id == $0", "$id").find().first()
    }

    fun selectMaxID(): Int {
        return connection.query<Achievement>().max("id", Int::class).find() ?: 0
    }
}