package ru.vo1d.web.app.exposed.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface DbManager {
    val newsDb: Database?
    val qnaDb: Database?
    val daybookDb: Database?

    fun init()

    suspend fun <T> query(db: Database?, block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, db) { block() }
}