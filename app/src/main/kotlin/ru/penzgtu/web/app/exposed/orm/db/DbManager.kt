package ru.penzgtu.web.app.exposed.orm.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface DbManager {
    val newsDb: Database?
    val qnaDb: Database?
    val timetableDb: Database?

    fun init()

    suspend fun <T> query(db: Database?, block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, db) { block() }
}