package ru.vo1d.web.exposed.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.exposed.entities.HasModel
import ru.vo1d.web.exposed.utils.tables.StringEntity
import ru.vo1d.web.exposed.utils.tables.StringEntityClass
import ru.vo1d.web.exposed.utils.tables.StringIdTable

object GraduationDegrees : StringIdTable(idColumnLength = 16) {
    val title = varchar("title", 16)
}

class GraduationDegreeEntity(id: EntityID<String>) : StringEntity(id), HasModel<GraduationDegree> {
    companion object : StringEntityClass<GraduationDegreeEntity>(GraduationDegrees)

    val title by GraduationDegrees.title

    override fun toModel() = GraduationDegree(id.value, title)
}