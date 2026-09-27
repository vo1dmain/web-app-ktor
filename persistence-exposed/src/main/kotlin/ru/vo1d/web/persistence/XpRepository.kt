package ru.vo1d.web.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

/**
 * Base for Exposed repositories: runs query in a transaction on [db] off the caller's thread.
 */
abstract class XpRepository(private val db: Database) {
    protected suspend fun <T> query(block: suspend Transaction.() -> T): T = withContext(Dispatchers.IO) {
        suspendTransaction(db) { block() }
    }
}
