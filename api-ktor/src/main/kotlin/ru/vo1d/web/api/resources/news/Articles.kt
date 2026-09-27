package ru.vo1d.web.api.resources.news

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vo1d.web.api.extensions.failIf
import ru.vo1d.web.api.extensions.failIfNegative
import ru.vo1d.web.api.extensions.failIfNotPositive
import ru.vo1d.web.api.resources.ListResource

@Serializable
@Resource("/articles")
data class Articles(
    override val page: Int? = null,
    @SerialName("category") val categories: List<Int>? = null
) : ListResource {
    init {
        page?.failIfNotPositive()
        categories?.failIf { any { it < 0 } }
    }


    @Serializable
    @Resource("/articles/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }
    }
}