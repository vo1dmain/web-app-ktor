package ru.vo1d.web.api.errors

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int,
    val reason: String
)
