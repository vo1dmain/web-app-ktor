package ru.vo1d.web.orm.entities.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.orm.entities.HasModel

object GraduationDegrees : IdTable<String>() {
    override val id = varchar("id", 16).uniqueIndex().entityId()
    val title = varchar("title", 16)
}

class GraduationDegree(id: EntityID<String>) : Entity<String>(id), HasModel<GradDegreeModel> {
    companion object : EntityClass<String, GraduationDegree>(GraduationDegrees)

    val title by GraduationDegrees.title

    override fun toModel() = GradDegreeModel(id.value, title)
}