package ru.vo1d.web.exposed.dao.qna

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.AnswerDao
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.qna.AnswerEntity
import ru.vo1d.web.exposed.entities.qna.Answers
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class AnswerDaoXp(ctx: DbContext) : XpDao(ctx.qna), AnswerDao {
    override suspend fun create(item: Answer): Int = query {
        Answers.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Answer): Int = query {
        Answers.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Answer? = query {
        AnswerEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Answer): Int = query {
        Answers.update({ Answers.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Answer): Int = query {
        Answers.deleteWhere { Answers.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<Answer> = query {
        AnswerEntity.all()
            .limit(limit)
            .offset(offset)
            .map(AnswerEntity::toDomain)
    }
}
