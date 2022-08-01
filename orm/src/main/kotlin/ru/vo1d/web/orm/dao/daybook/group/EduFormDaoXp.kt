package ru.vo1d.web.orm.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.EduFormDao
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.group.EducationFormEntity
import ru.vo1d.web.orm.entities.daybook.group.EducationForms

class EduFormDaoXp : EduFormDao, XpDao<EducationForm> {
    override suspend fun create(item: EducationForm) =
        EducationForms.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: EducationForm) =
        EducationForms.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: String) =
        EducationFormEntity.findById(id)?.toModel()

    override suspend fun update(item: EducationForm) =
        EducationForms.update({ EducationForms.id eq item.id }) { it[title] = item.title }

    override suspend fun delete(id: String) =
        EducationForms.deleteWhere { EducationForms.id eq id }

    override suspend fun all() =
        EducationFormEntity.all().map(EducationFormEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: EducationForm) {
        this[EducationForms.id] = item.id
        this[EducationForms.title] = item.title
    }
}