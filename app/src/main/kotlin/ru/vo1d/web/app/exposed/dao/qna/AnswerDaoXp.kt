package ru.vo1d.web.app.exposed.dao.qna

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.AnswerDao
import ru.vo1d.web.app.exposed.orm.qna.Answer
import ru.vo1d.web.app.exposed.orm.qna.Answers
import ru.vo1d.web.entities.qna.answer.AnswerModel

class AnswerDaoXp : AnswerDao {
    override suspend fun create(item: AnswerModel) = Answers.insertAndGetId {
        it[questionId] = item.questionId
        it[body] = item.body
        it[dateTime] = item.dateTime
    }.value

    override suspend fun read(id: Int) = Answer.findById(id)?.toModel()

    override suspend fun update(item: AnswerModel) = Answers.update({ Answers.id eq item.id }) {
        it[questionId] = item.questionId
        it[body] = item.body
        it[dateTime] = item.dateTime
    }

    override suspend fun delete(id: Int) = Answers.deleteWhere { Answers.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Answer.all().limit(limit, offset).map(Answer::toModel)
}