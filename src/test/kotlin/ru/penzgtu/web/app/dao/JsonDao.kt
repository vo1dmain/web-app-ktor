package ru.penzgtu.web.app.dao

import kotlinx.serialization.json.Json

sealed interface JsonDao {
    val json get() = Json { ignoreUnknownKeys = true }
}