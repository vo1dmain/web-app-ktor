package ru.vo1d.web.exposed.context

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ru.vo1d.web.exposed.utils.XpDslMarker

@XpDslMarker
abstract class DbContext {
    internal abstract val newsDb: Database?
    internal abstract val qnaDb: Database?
    internal abstract val daybookDb: Database?

    abstract fun init()

    internal suspend fun <T> query(db: Database?, block: suspend Transaction.() -> T) =
        newSuspendedTransaction(Dispatchers.IO, db, null, block)

    suspend operator fun <T> invoke(block: suspend DbContext.() -> T) = withContext(Dispatchers.IO) { block() }
}