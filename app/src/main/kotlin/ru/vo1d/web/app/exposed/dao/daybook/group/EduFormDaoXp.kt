package ru.vo1d.web.app.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.EduFormDao
import ru.vo1d.web.app.exposed.orm.daybook.group.EducationForm
import ru.vo1d.web.app.exposed.orm.daybook.group.EducationForms
import ru.vo1d.web.entities.daybook.group.form.EduFormModel

class EduFormDaoXp : EduFormDao {
    override suspend fun create(item: EduFormModel) = EducationForms.insertAndGetId {
        it[id] = item.id
        it[title] = item.title
    }.value

    override suspend fun read(id: String) = EducationForm.findById(id)?.toModel()

    override suspend fun update(item: EduFormModel) = EducationForms.update({ EducationForms.id eq item.id }) {
        it[title] = item.title
    }

    override suspend fun delete(id: String) = EducationForms.deleteWhere { EducationForms.id eq id }

    override suspend fun all() = EducationForm.all().map(EducationForm::toModel)
}