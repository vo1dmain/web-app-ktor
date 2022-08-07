package ru.vo1d.web.server.dao

import kotlinx.serialization.json.Json

sealed interface JsonDao {
    val json get() = Json { ignoreUnknownKeys = true }
}