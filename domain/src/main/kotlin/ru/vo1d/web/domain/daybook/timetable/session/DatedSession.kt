package ru.vo1d.web.domain.daybook.timetable.session

import kotlin.time.Duration
import kotlin.time.Instant

/**
 * A one-off session at [dateTime].
 */
data class DatedSession(
    override val id: Int,
    override val subject: String,
    override val instructor: String,
    override val place: String,
    override val typeId: Int,
    override val duration: Duration,
    val dateTime: Instant
) : Session

/**
 * @param duration null for the default session length
 */
data class NewDatedSession(
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration?,
    val dateTime: Instant
)
