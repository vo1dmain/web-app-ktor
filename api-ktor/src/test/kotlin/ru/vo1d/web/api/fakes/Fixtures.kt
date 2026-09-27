package ru.vo1d.web.api.fakes

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.extensions.open
import ru.vo1d.web.domain.extensions.resource

/**
 * JSON fixtures from `src/test/resources/data`.
 */
internal object Fixtures {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun <T> list(path: String, serializer: KSerializer<T>): List<T> = resource(path).open {
        json.decodeFromStream(ListSerializer(serializer), this)
    }
}

internal fun <T> List<T>.page(page: PageRequest) = drop(page.offset.toInt()).take(page.size)
