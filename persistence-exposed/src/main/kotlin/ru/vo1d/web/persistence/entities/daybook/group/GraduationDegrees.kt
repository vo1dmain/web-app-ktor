package ru.vo1d.web.persistence.entities.daybook.group

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import ru.vo1d.web.persistence.tables.StringEntity
import ru.vo1d.web.persistence.tables.StringEntityClass
import ru.vo1d.web.persistence.tables.StringIdTable

internal object GraduationDegrees : StringIdTable(idColumnLength = 16) {
    val title: Column<String> = varchar("title", 16)
}

internal class GraduationDegreeEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<GraduationDegreeEntity>(GraduationDegrees)

    val title by GraduationDegrees.title
}