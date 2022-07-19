package ru.vo1d.web.app.resources.news

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative

@Serializable
@Resource("/articles")
data class Articles(
    val page: Int? = null,
    val category: Int? = null
) {
    init {
        page?.failIfNegative()
        category?.failIfNegative()
    }


    @Serializable
    @Resource("/articles/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }
    }
}