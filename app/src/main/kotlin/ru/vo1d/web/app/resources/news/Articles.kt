package ru.vo1d.web.app.resources.news

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.resources.ListResource

@Serializable
@Resource("/articles")
data class Articles(
    override val page: Int? = null,
    val category: Int? = null
) : ListResource {
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