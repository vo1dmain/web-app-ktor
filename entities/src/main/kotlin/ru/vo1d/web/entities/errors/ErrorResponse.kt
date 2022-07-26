package ru.vo1d.web.entities.errors

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int,
    val reason: String
)
