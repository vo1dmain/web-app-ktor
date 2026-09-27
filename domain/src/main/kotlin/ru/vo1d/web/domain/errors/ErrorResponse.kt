package ru.vo1d.web.domain.errors

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int,
    val reason: String
)
