package ru.vo1d.web.domain.daybook.group

data class Group(
    val code: String,
    val levelId: String,
    val degreeId: String?,
    val formId: String,
    val tableTypes: List<String>? = null
)
