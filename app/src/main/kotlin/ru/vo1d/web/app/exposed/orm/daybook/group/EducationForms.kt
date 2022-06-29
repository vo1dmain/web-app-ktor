package ru.vo1d.web.app.exposed.orm.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import ru.vo1d.web.app.exposed.orm.HasModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel

object EducationForms : IdTable<String>() {
    override val id = varchar("id", 16).uniqueIndex().entityId()
    val title = varchar("title", 32)
}

class EducationForm(id: EntityID<String>) : Entity<String>(id), HasModel<EduFormModel> {
    companion object : EntityClass<String, EducationForm>(EducationForms)

    val title by EducationForms.title

    override fun toModel() = EduFormModel(id.value, title)
}