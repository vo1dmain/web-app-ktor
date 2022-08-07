package ru.vo1d.web.server.resources.qna

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.server.extensions.failIfNegative
import ru.vo1d.web.server.resources.ListResource

@Serializable
@Resource("/posts")
data class Posts(override val page: Int? = null) : ListResource {
    init {
        page?.failIfNegative()
    }

    @Serializable
    @Resource("/posts/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }
    }
}
