package ru.vo1d.web.server.resources.news

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vo1d.web.server.extensions.failIf
import ru.vo1d.web.server.extensions.failIfNegative
import ru.vo1d.web.server.resources.ListResource

@Serializable
@Resource("/articles")
data class Articles(
    override val page: Int? = null,
    @SerialName("category") val categories: List<Int>? = null
) : ListResource {
    init {
        page?.failIfNegative()
        categories?.failIf { all { it < 0 } }
    }


    @Serializable
    @Resource("/articles/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }
    }
}