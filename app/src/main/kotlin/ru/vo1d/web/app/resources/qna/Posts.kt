package ru.vo1d.web.app.resources.qna

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative

@Serializable
@Resource("/posts")
data class Posts(val page: Int? = null) {
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
