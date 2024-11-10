package ru.vo1d.web.exposed.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.exposed.tables.StringEntity
import ru.vo1d.web.exposed.tables.StringEntityClass
import ru.vo1d.web.exposed.tables.StringIdTable

internal object GraduationLevels : StringIdTable(idColumnLength = 16) {
    val title = varchar("title", 64)
}

internal class GraduationLevelEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<GraduationLevelEntity>(GraduationLevels)

    val title by GraduationLevels.title
}