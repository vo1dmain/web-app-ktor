package ru.vo1d.web.persistence.daybook

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.jdbc.batchInsert
import ru.vo1d.web.persistence.readSeed
import ru.vo1d.web.persistence.tables.StringIdTable

@Serializable
private class CodedTitle(val id: String, val title: String)

@Serializable
private class Title(val title: String)

/**
 * Inserts the daybook lookup lists from `data/daybook`; rows that already exist are left as they are,
 * so it is safe on every start.
 */
internal fun seedReferenceData() {
    insertCoded(GraduationLevels, GraduationLevels.title, "daybook/group/levels.json")
    insertCoded(GraduationDegrees, GraduationDegrees.title, "daybook/group/degrees.json")
    insertCoded(EducationForms, EducationForms.title, "daybook/group/forms.json")
    insertCoded(TableTypes, TableTypes.title, "daybook/group/types.json")

    // no ids in the file: the unique title identifies a type
    val sessionTypes = readSeed("daybook/timetable/session-types.json", Title.serializer())
    SessionTypes.batchInsert(sessionTypes, ignore = true, shouldReturnGeneratedValues = false) {
        this[SessionTypes.title] = it.title
    }
}

private fun insertCoded(table: StringIdTable, title: Column<String>, path: String) {
    table.batchInsert(readSeed(path, CodedTitle.serializer()), ignore = true, shouldReturnGeneratedValues = false) {
        this[table.id] = it.id
        this[title] = it.title
    }
}
