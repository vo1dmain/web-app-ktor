package ru.vo1d.web.orm.dao.qna

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.entities.qna.question.QuestionModel
import ru.vo1d.web.orm.entities.qna.Question
import ru.vo1d.web.orm.entities.qna.Questions

class QuestionDaoXp : QuestionDao {
    override suspend fun create(item: QuestionModel) = Questions.insertAndGetId {
        it[theme] = item.theme
        it[body] = item.body
        it[acceptorId] = item.acceptorId
        it[email] = item.email
        item.dateTime?.let { itemDateTime ->
            it[dateTime] = itemDateTime
        }
        item.timeZone?.let { itemTimeZone ->
            it[timeZone] = itemTimeZone.id
        }
    }.value

    override suspend fun read(id: Int) = Question.findById(id)?.toModel()

    override suspend fun update(item: QuestionModel) = Questions.update({ Questions.id eq item.id }) {
        it[theme] = item.theme
        it[body] = item.body
        it[acceptorId] = item.acceptorId
        it[email] = item.email
        item.dateTime?.let { itemDateTime ->
            it[dateTime] = itemDateTime
        }
        item.timeZone?.let { itemTimeZone ->
            it[timeZone] = itemTimeZone.id
        }
    }

    override suspend fun delete(id: Int) = Questions.deleteWhere { Questions.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Question.all().limit(limit, offset).map(Question::toModel)
}