package ru.vo1d.web.server.resources.qna

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.server.extensions.failIfNegative
import ru.vo1d.web.server.resources.ListResource

@Serializable
@Resource("/questions")
data class Questions(override val page: Int? = null) : ListResource {
    init {
        page?.failIfNegative()
    }

    @Serializable
    @Resource("/questions/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }
    }
}
