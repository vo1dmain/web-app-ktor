package ru.vo1d.web.exposed.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.exposed.tables.StringEntity
import ru.vo1d.web.exposed.tables.StringEntityClass
import ru.vo1d.web.exposed.tables.StringIdTable

internal object GraduationDegrees : StringIdTable(idColumnLength = 16) {
    val title = varchar("title", 16)
}

internal class GraduationDegreeEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<GraduationDegreeEntity>(GraduationDegrees)

    val title by GraduationDegrees.title
}