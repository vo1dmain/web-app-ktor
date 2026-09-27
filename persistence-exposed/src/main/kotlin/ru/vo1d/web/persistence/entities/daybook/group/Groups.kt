package ru.vo1d.web.persistence.entities.daybook.group

import org.jetbrains.exposed.v1.core.ReferenceOption.CASCADE
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import ru.vo1d.web.persistence.entities.daybook.timetable.Timetables
import ru.vo1d.web.persistence.tables.StringEntity
import ru.vo1d.web.persistence.tables.StringEntityClass
import ru.vo1d.web.persistence.tables.StringIdTable

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