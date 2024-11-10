package ru.vo1d.web.exposed.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.exposed.entities.daybook.timetable.Timetables
import ru.vo1d.web.exposed.tables.StringEntity
import ru.vo1d.web.exposed.tables.StringEntityClass
import ru.vo1d.web.exposed.tables.StringIdTable

internal object Groups : StringIdTable(idColumnLength = 16) {
    val levelId = reference("levelId", GraduationLevels, CASCADE, CASCADE)
    val degreeId = optReference("degreeId", GraduationDegrees, CASCADE, CASCADE)
    val formId = reference("formId", EducationForms, CASCADE, CASCADE)
}

internal open class GroupEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<GroupEntity>(Groups)

    val levelId by Groups.levelId
    val degreeId by Groups.degreeId
    val formId by Groups.formId
    val types by TableTypeEntity via Timetables
}