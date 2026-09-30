package ru.vo1d.web.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import ru.vo1d.web.domain.errors.AlreadyExistsException
import ru.vo1d.web.domain.errors.MissingReferenceException
import java.sql.SQLException

/**
 * Base for Exposed repositories: runs query in a transaction on [db] off the caller's thread.
 * Constraint violations come out as domain errors, see [toDomainError].
 */
abstract class XpRepository(private val db: Database) {
    protected suspend fun <T> query(block: suspend Transaction.() -> T): T = withContext(Dispatchers.IO) {
        try {
            suspendTransaction(db) { block() }
        } catch (e: ExposedSQLException) {
            throw e.toDomainError() ?: e
        }
    }
}

/**
 * Integrity constraint violations by SQLSTATE: 23505 is a unique key, 23503/23506 a foreign key
 * (H2 reports a missing parent as 23506).
 */
private fun ExposedSQLException.toDomainError(): RuntimeException? {
    val state = (cause as? SQLException)?.sqlState ?: sqlState
    return when (state) {
        "23505" -> AlreadyExistsException("Entity already exists", this)
        "23503", "23506" -> MissingReferenceException(cause = this)
        else -> null
    }
}
