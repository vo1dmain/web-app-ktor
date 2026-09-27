package ru.vo1d.web.domain.daybook

import kotlinx.datetime.*
import ru.vo1d.web.domain.daybook.timetable.week.Week
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters
import kotlin.time.Clock

/**
 * @param timeZone zone of the whole daybook: the week turns over at its midnight.
 */
class DaybookService(
    private val reference: ReferenceRepository,
    private val timeZone: TimeZone,
    private val clock: Clock = Clock.System
) {
    suspend fun meta() = Meta(
        week(),
        reference.levels(),
        reference.degrees(),
        reference.forms(),
        reference.tableTypes(),
        reference.groups(),
        timeZone.id,
        reference.sessionTypes()
    )

    /**
     * Weeks alternate starting from the one containing September 1st.
     */
    fun week(): Week {
        val today = clock.todayIn(timeZone)
        val thisYearS1 = LocalDate(today.year, Month.SEPTEMBER, 1)

        if (today == thisYearS1) return Week.FIRST

        val lastYearFirstWeekStart = when (today < thisYearS1) {
            true -> LocalDate(today.year - 1, Month.SEPTEMBER, 1)
            else -> thisYearS1
        }.toJavaLocalDate()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .toKotlinLocalDate()

        val weeksBetween = lastYearFirstWeekStart.until(today, DateTimeUnit.WEEK)

        return when (weeksBetween % 2) {
            0L -> Week.FIRST
            else -> Week.SECOND
        }
    }
}
