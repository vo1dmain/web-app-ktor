package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import ru.vo1d.web.persistence.tables.StringEntity
import ru.vo1d.web.persistence.tables.StringEntityClass
import ru.vo1d.web.persistence.tables.StringIdTable

internal object EducationForms : StringIdTable(idColumnLength = 16) {
    val title = varchar("title", 32)
}

internal class EducationFormEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<EducationFormEntity>(EducationForms)

    val title by EducationForms.title
}