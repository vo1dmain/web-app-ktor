package ru.vo1d.web.api.dao

import kotlinx.serialization.json.Json

interface JsonDao {
    val json get() = Json { ignoreUnknownKeys = true }
}