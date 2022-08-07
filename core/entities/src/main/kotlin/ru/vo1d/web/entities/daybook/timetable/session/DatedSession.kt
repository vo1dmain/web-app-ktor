package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Dated")
data class DatedSession(
    val id: Int? = null,
    override val subject: String,
    override val instructor: String,
    override val place: String,
    override val typeId: Int,
    override val duration: DateTimePeriod? = null,
    val datetime: LocalDateTime,
    val timeZone: TimeZone? = null
) : Session
