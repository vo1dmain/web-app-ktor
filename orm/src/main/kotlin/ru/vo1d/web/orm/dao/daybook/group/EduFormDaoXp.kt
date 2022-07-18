package ru.vo1d.web.orm.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.EduFormDao
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.group.EducationForm
import ru.vo1d.web.orm.entities.daybook.group.EducationForms

class EduFormDaoXp : EduFormDao, XpDao<EduFormModel> {
    override suspend fun create(item: EduFormModel) = EducationForms.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: EduFormModel) =
        EducationForms.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: String) = EducationForm.findById(id)?.toModel()

    override suspend fun update(item: EduFormModel) =
        EducationForms.update({ EducationForms.id eq item.id }) {
            it[title] = item.title
        }

    override suspend fun delete(id: String) = EducationForms.deleteWhere { EducationForms.id eq id }

    override suspend fun all() = EducationForm.all().map(EducationForm::toModel)

    override fun UpdateBuilder<Int>.mapItem(item: EduFormModel) {
        this[EducationForms.id] = item.id
        this[EducationForms.title] = item.title
    }
}