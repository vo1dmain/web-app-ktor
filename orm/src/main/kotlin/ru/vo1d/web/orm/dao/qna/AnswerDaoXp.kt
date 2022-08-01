package ru.vo1d.web.orm.dao.qna

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.AnswerDao
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.qna.AnswerEntity
import ru.vo1d.web.orm.entities.qna.Answers

class AnswerDaoXp : AnswerDao, XpDao<Answer> {
    override suspend fun create(item: Answer) =
        Answers.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: Answer) =
        Answers.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        AnswerEntity.findById(id)?.toModel()

    override suspend fun update(item: Answer) =
        Answers.update({ Answers.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        Answers.deleteWhere { Answers.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        AnswerEntity.all().limit(limit, offset).map(AnswerEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: Answer) {
        this[Answers.questionId] = item.questionId
        this[Answers.body] = item.body
        item.dateTime?.let { this[Answers.dateTime] = it }
        item.timeZone?.let { this[Answers.timeZone] = it.id }
    }
}