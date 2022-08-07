package ru.vo1d.web.exposed.repos

import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.utils.XpDslMarker

@XpDslMarker
sealed interface XpRepo {
    val dbContext: DbContext
}