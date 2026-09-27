package ru.vo1d.web.api.resources.qna

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.api.extensions.failIfNegative
import ru.vo1d.web.api.extensions.failIfNotPositive
import ru.vo1d.web.api.resources.ListResource

@Serializable
@Resource("/posts")
data class Posts(override val page: Int? = null) : ListResource {
    init {
        page?.failIfNotPositive()
    }

    @Serializable
    @Resource("/posts/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }
    }
}
