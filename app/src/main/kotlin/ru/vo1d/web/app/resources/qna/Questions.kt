package ru.vo1d.web.app.resources.qna

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative

@Serializable
@Resource("/questions")
data class Questions(val page: Int? = null) {
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
