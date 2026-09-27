package ru.vo1d.web.domain.daybook.timetable.session

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Instant

@Serializable
@SerialName("Dated")
data class DatedSession(
    val id: Int? = null,
    override val subject: String,
    override val instructor: String,
    override val place: String,
    override val typeId: Int,
    override val duration: Duration? = null,
    val dateTime: Instant
) : Session
