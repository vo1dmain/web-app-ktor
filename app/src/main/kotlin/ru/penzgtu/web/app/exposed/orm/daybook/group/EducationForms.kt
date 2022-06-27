package ru.penzgtu.web.app.exposed.orm.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.entities.daybook.group.form.EducationFormModel

object EducationForms : IdTable<String>() {
    override val id = varchar("id", 16).entityId()
    val title = varchar("title", 32)
}

class EducationForm(id: EntityID<String>) : Entity<String>(id), HasModel<EducationFormModel> {
    companion object : EntityClass<String, EducationForm>(EducationForms)

    val title by EducationForms.title

    override fun toModel() = EducationFormModel(id.value, title)
}