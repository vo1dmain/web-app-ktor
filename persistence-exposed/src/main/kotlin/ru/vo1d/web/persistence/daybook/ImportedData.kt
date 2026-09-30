package ru.vo1d.web.persistence.daybook

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.time.Duration
import kotlin.time.Instant

/*
 * Format of a timetable export made outside the app (groups with their timetables and sessions).
 * Ids refer to the app's reference data.
 */

@Serializable
private class ImportFile(val groups: List<ImportGroup>, val timetables: List<ImportTimetable>)

@Serializable
private class ImportGroup(val code: String, val levelId: String, val degreeId: String?, val formId: String)

@Serializable
private class ImportTimetable(
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat,
    val regular: List<ImportRegularSession> = emptyList(),
    val dated: List<ImportDatedSession> = emptyList()
)

@Serializable
private class ImportRegularSession(
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val weekOption: WeekOption
)

@Serializable
private class ImportDatedSession(
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration,
    val dateTime: Instant
)

private val importJson = Json { ignoreUnknownKeys = true }

/**
 * Loads groups and timetables from [file]. Existing groups are kept, and a timetable that already exists is
 * skipped with its sessions, so loading the same file again changes nothing.
 */
internal fun seedImportedTimetables(file: Path) {
    require(file.exists()) { "Timetable import file not found: ${file.toAbsolutePath()}" }
    val import = importJson.decodeFromString<ImportFile>(file.readText())

    Groups.batchInsert(import.groups, ignore = true, shouldReturnGeneratedValues = false) {
        this[Groups.id] = it.code
        this[Groups.levelId] = it.levelId
        this[Groups.degreeId] = it.degreeId
        this[Groups.formId] = it.formId
    }

    import.timetables.forEach { timetable ->
        val timetableId = Timetables.insertIgnoreAndGetId {
            it[groupCode] = timetable.groupCode
            it[typeId] = timetable.typeId
            it[format] = timetable.format
        } ?: return@forEach

        timetable.regular.forEach { session ->
            val sessionId = RegularSessions.insertAndGetId {
                it[subject] = session.subject.take(160)
                it[instructor] = session.instructor.take(64)
                it[place] = session.place.take(32)
                it[typeId] = session.typeId
                it[duration] = session.duration
                it[dayOfWeek] = session.dayOfWeek
                it[time] = session.time
                it[weekOption] = session.weekOption
            }
            TimetableRegularSessions.insert {
                it[this.timetableId] = timetableId
                it[this.sessionId] = sessionId
            }
        }

        timetable.dated.forEach { session ->
            val sessionId = DatedSessions.insertAndGetId {
                it[subject] = session.subject.take(160)
                it[instructor] = session.instructor.take(64)
                it[place] = session.place.take(32)
                it[typeId] = session.typeId
                it[duration] = session.duration
                it[dateTime] = session.dateTime
            }
            TimetableDatedSessions.insert {
                it[this.timetableId] = timetableId
                it[this.sessionId] = sessionId
            }
        }
    }
}
