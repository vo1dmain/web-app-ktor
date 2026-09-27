package ru.vo1d.web.domain.extensions

import kotlinx.datetime.DateTimePeriod
import kotlin.time.Duration

fun DateTimePeriod.toDuration() = Duration.parseIsoString(this.toString())