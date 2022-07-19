package ru.vo1d.web.orm.entities.qna

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.orm.entities.HasModel

object Answers : IntIdTable() {
    val questionId = reference("questionId", Questions, CASCADE, CASCADE)
    val body = varchar("body", 1024)
    val dateTime = datetime("dateTime").defaultExpression(CurrentDateTime)
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

class Answer(id: EntityID<Int>) : IntEntity(id), HasModel<AnswerModel> {
    companion object : IntEntityClass<Answer>(Answers)

    val questionId by Answers.questionId
    val body by Answers.body
    val dateTime by Answers.dateTime
    val timeZone by Answers.timeZone

    override fun toModel() = AnswerModel(id.value, questionId.value, body, dateTime, TimeZone.of(timeZone))
}