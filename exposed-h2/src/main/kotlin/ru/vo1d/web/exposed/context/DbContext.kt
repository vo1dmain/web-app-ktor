package ru.vo1d.web.exposed.context

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

abstract class DbContext {
    internal abstract val newsDb: Database?
    internal abstract val qnaDb: Database?
    internal abstract val daybookDb: Database?

    abstract fun init()

    internal suspend fun <T> query(db: Database?, block: suspend Transaction.() -> T) =
        newSuspendedTransaction(Dispatchers.IO, db, null, block)
}