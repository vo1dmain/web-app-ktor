package ru.vo1d.web.persistence

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

private val seedJson = Json { ignoreUnknownKeys = true }

/**
 * Reads a JSON list shipped in the module's resources under `data/`.
 */
internal fun <T> readSeed(path: String, serializer: KSerializer<T>): List<T> {
    val text = XpRepository::class.java.getResource("/data/$path")?.readText()
        ?: error("Seed file not found: data/$path")
    return seedJson.decodeFromString(ListSerializer(serializer), text)
}
