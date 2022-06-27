package ru.vo1d.web.app.dao

import kotlinx.serialization.json.Json

sealed interface JsonDao {
    val json get() = Json { ignoreUnknownKeys = true }
}