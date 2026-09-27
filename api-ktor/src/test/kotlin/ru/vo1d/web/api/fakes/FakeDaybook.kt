package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.*
import ru.vo1d.web.domain.daybook.group.*
import ru.vo1d.web.domain.daybook.timetable.NewTimetable
import ru.vo1d.web.domain.daybook.timetable.Timetable
import ru.vo1d.web.domain.daybook.timetable.session.*
import ru.vo1d.web.domain.errors.AlreadyExistsException
import ru.vo1d.web.domain.errors.MissingReferenceException
import ru.vo1d.web.domain.DEFAULT_DURATION

/*
 * In-memory daybook storage that fails the way the database does: unknown references and duplicate timetables.
 */

class FakeReferenceRepository : ReferenceRepository {
    val groupCodes get() = groups.map { it.code }.toSet()
    val typeIds get() = tableTypes.map { it.id }.toSet()
    val sessionTypeIds get() = sessionTypes.map { it.id }.toSet()

    private val levels = listOf(GraduationLevel("high", "Высшее образование"))
    private val degrees = listOf(GraduationDegree("bachelor", "Бакалавриат"))
    private val forms = listOf(EducationForm("full-time", "Очная"))
    private val tableTypes = listOf(TableType("tt", "Занятия"), TableType("att", "Аттестация"))
    private val sessionTypes = listOf(SessionType(1, "Лекция"), SessionType(2, "Практика"))
    private val groups = listOf(Group("ИВТ-21", "high", "bachelor", "full-time"))

    override suspend fun levels() = levels
    override suspend fun degrees() = degrees
    override suspend fun forms() = forms
    override suspend fun tableTypes() = tableTypes
    override suspend fun sessionTypes() = sessionTypes
    override suspend fun groups() = groups
}

class FakeTimetableRepository(private val reference: FakeReferenceRepository) : TimetableRepository {
    private val stored = mutableListOf<Timetable>()

    override suspend fun find(filters: TimetableFilters, page: PageRequest) = stored
        .filter { filters.groupCode?.equals(it.groupCode) ?: true }
        .filter { filters.typeId?.equals(it.typeId) ?: true }
        .filter { filters.format?.equals(it.format) ?: true }
        .page(page)

    override suspend fun get(id: Int) = stored.firstOrNull { it.id == id }

    override suspend fun add(timetable: NewTimetable): Int {
        if (timetable.groupCode !in reference.groupCodes || timetable.typeId !in reference.typeIds) {
            throw MissingReferenceException()
        }
        if (stored.any { it.groupCode == timetable.groupCode && it.typeId == timetable.typeId && it.format == timetable.format }) {
            throw AlreadyExistsException("The group already has such a timetable")
        }
        val id = stored.size + 1
        stored += Timetable(id, timetable.groupCode, timetable.typeId, timetable.format)
        return id
    }
}

class FakeRegularSessionRepository(private val reference: FakeReferenceRepository) : RegularSessionRepository {
    private val stored = mutableListOf<RegularSession>()
    private val links = mutableSetOf<TimetableSession>()

    override suspend fun find(filters: RegularSessionFilters, page: PageRequest) = stored
        .filter { session -> filters.timetableId?.let { TimetableSession(it, session.id) in links } ?: true }
        .filter { filters.dayOfWeek?.equals(it.dayOfWeek) ?: true }
        .filter { filters.time?.equals(it.time) ?: true }
        .sortedWith(compareBy({ it.dayOfWeek }, { it.time }))
        .page(page)

    override suspend fun add(session: NewRegularSession): Int {
        if (session.typeId !in reference.sessionTypeIds) throw MissingReferenceException()
        val id = stored.size + 1
        stored += with(session) {
            RegularSession(id, subject, instructor, place, typeId, duration ?: DEFAULT_DURATION, dayOfWeek, time, weekOption)
        }
        return id
    }

    override suspend fun attach(link: TimetableSession) {
        if (stored.none { it.id == link.sessionId }) throw MissingReferenceException()
        links += link
    }
}

class FakeDatedSessionRepository(private val reference: FakeReferenceRepository) : DatedSessionRepository {
    private val stored = mutableListOf<DatedSession>()
    private val links = mutableSetOf<TimetableSession>()

    override suspend fun find(filters: DatedSessionFilters, page: PageRequest) = stored
        .filter { session -> filters.timetableId?.let { TimetableSession(it, session.id) in links } ?: true }
        .filter { filters.dateTime?.equals(it.dateTime) ?: true }
        .sortedBy { it.dateTime }
        .page(page)

    override suspend fun add(session: NewDatedSession): Int {
        if (session.typeId !in reference.sessionTypeIds) throw MissingReferenceException()
        val id = stored.size + 1
        stored += with(session) {
            DatedSession(id, subject, instructor, place, typeId, duration ?: DEFAULT_DURATION, dateTime)
        }
        return id
    }

    override suspend fun attach(link: TimetableSession) {
        if (stored.none { it.id == link.sessionId }) throw MissingReferenceException()
        links += link
    }
}
