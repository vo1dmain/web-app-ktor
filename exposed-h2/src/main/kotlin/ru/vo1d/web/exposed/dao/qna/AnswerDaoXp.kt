package ru.vo1d.web.exposed.dao.qna

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.AnswerDao
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.exposed.entities.qna.AnswerEntity
import ru.vo1d.web.exposed.entities.qna.Answers
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class AnswerDaoXp : AnswerDao {
    override suspend fun create(item: Answer): Int {
        return Answers.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Answer): Int {
        return Answers.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Answer? {
        return AnswerEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Answer): Int {
        return Answers.update({ Answers.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(items: Array<out Answer>): Int {
        return Answers.deleteWhere { Answers.id eq id }
    }

    override suspend fun page(offset: Long, limit: Int): List<Answer> {
        return AnswerEntity.all()
            .limit(limit)
            .offset(offset)
            .map(AnswerEntity::toDomain)
    }
}