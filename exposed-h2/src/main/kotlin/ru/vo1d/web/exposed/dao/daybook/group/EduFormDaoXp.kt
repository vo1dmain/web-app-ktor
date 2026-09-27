package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.EduFormDao
import ru.vo1d.web.entities.daybook.group.EducationForm
import ru.vo1d.web.exposed.entities.daybook.group.EducationFormEntity
import ru.vo1d.web.exposed.entities.daybook.group.EducationForms
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class EduFormDaoXp : EduFormDao {
    override suspend fun create(item: EducationForm): String? {
        return EducationForms.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: EducationForm): Int {
        return EducationForms.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): EducationForm? {
        return EducationFormEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: EducationForm): Int {
        return EducationForms.update({ EducationForms.id eq item.id }) { it[title] = item.title }
    }

    override suspend fun delete(vararg items: EducationForm): Int {
        return EducationForms.deleteWhere { EducationForms.id inList items.map { it.id } }
    }

    override suspend fun all(): List<EducationForm> {
        return EducationFormEntity.all().map(EducationFormEntity::toDomain)
    }
}