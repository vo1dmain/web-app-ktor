package ru.vo1d.web.exposed.dao.qna

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.entities.qna.question.Question
import ru.vo1d.web.exposed.entities.qna.QuestionEntity
import ru.vo1d.web.exposed.entities.qna.Questions
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class QuestionDaoXp : QuestionDao {
    override suspend fun create(item: Question): Int {
        return Questions.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Question): Int {
        return Questions.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Question? {
        return QuestionEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Question): Int {
        return Questions.update({ Questions.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Question): Int {
        return Questions.deleteWhere { Questions.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<Question> {
        return QuestionEntity.all()
            .limit(limit)
            .offset(offset)
            .map(QuestionEntity::toDomain)
    }
}