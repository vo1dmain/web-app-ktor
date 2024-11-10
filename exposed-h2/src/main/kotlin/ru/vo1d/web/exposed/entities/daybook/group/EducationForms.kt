package ru.vo1d.web.exposed.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.exposed.tables.StringEntity
import ru.vo1d.web.exposed.tables.StringEntityClass
import ru.vo1d.web.exposed.tables.StringIdTable

internal object EducationForms : StringIdTable(idColumnLength = 16) {
    val title = varchar("title", 32)
}

internal class EducationFormEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<EducationFormEntity>(EducationForms)

    val title by EducationForms.title
}