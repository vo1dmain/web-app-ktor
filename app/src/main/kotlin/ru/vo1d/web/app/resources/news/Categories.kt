package ru.vo1d.web.app.resources.news

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative

@Serializable
@Resource("/categories")
data class Categories(
    val page: Int? = null,
    val parent: Int? = null
) {
    init {
        page?.failIfNegative()
        parent?.failIfNegative()
    }

    @Serializable
    @Resource("/categories/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }
    }
}
