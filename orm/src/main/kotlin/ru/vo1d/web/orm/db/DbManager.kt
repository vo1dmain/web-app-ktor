package ru.vo1d.web.orm.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ru.vo1d.web.orm.utils.XpDslMarker

@XpDslMarker
interface DbManager {
    val newsDb: Database?
    val qnaDb: Database?
    val daybookDb: Database?

    fun init()

    suspend fun <T> query(db: Database?, block: suspend Transaction.() -> T) = newSuspendedTransaction(
        context = Dispatchers.IO,
        db = db,
        statement = block
    )
}