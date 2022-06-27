package ru.penzgtu.web.app.exposed.dao.daybook

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.penzgtu.web.app.data.dao.EducationFormDao
import ru.penzgtu.web.app.exposed.orm.daybook.group.EducationForm
import ru.penzgtu.web.app.exposed.orm.daybook.group.EducationForms
import ru.penzgtu.web.entities.daybook.group.form.EducationFormModel

class EducationFormDaoXp : EducationFormDao {
    override suspend fun create(item: EducationFormModel): String {
        return EducationForms.insertAndGetId {
            it[id] = item.id
            it[title] = item.title
        }.value
    }

    override suspend fun read(id: String): EducationFormModel? {
        return EducationForm.findById(id)?.toModel()
    }

    override suspend fun update(item: EducationFormModel): Int {
        return EducationForms.update({ EducationForms.id eq item.id }) {
            it[title] = item.title
        }
    }

    override suspend fun delete(id: String): Int {
        return EducationForms.deleteWhere { EducationForms.id eq id }
    }

    override suspend fun all(): List<EducationFormModel> {
        return EducationForm.all().map(EducationForm::toModel)
    }
}