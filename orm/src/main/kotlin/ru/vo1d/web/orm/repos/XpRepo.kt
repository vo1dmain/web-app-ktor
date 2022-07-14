package ru.vo1d.web.orm.repos

import ru.vo1d.web.orm.db.DbManager
import ru.vo1d.web.orm.utils.XpDslMarker

@XpDslMarker
sealed interface XpRepo {
    val dbManager: DbManager

    suspend fun <T> dbManager(block: suspend DbManager.() -> T) = dbManager.block()
}