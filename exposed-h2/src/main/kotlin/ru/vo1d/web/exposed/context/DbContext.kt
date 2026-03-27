package ru.vo1d.web.exposed.context

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

abstract class DbContext {
    abstract fun init()

    internal suspend fun <T> query(db: Database?, block: suspend Transaction.() -> T): T {
        return suspendTransaction(db) {
            withContext(Dispatchers.IO) {
                block()
            }
        }
    }
}