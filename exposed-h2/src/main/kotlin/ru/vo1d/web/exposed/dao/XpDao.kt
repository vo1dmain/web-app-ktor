package ru.vo1d.web.exposed.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

/**
 * Base for Exposed DAOs: runs [block] in a transaction on [db] off the caller's thread.
 */
abstract class XpDao(private val db: Database) {
    protected suspend fun <T> query(block: suspend Transaction.() -> T): T = withContext(Dispatchers.IO) {
        suspendTransaction(db) { block() }
    }
}
